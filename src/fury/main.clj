(ns fury.main
  (:require [fury.vmfest]
            [fury.groups.fury]
            [fury.opts :as opts]
            [clojure.tools.logging :as logging]
            [pallet.api :as pallet]
            [pallet.compute :as compute]
            [pallet.node :as node]
            [pallet.crate.etc-hosts :as hosts]
            [pallet.actions :refer [exec-script*]]))

(defn quit []
  (shutdown-agents)
  (System/exit 0))

(defn die [errors]
  (logging/fatal errors)
  (System/exit 1))

(defn make-hosts [nodes]
  (let [f #(vector
            (node/primary-ip %) [(node/hostname %)])]
    (->> nodes
         (map f)
         (into {}))))

(defn build-and-run [{:keys [options]}]
  (let [svc (pallet/compute-service (:service options))
        session (pallet/converge {fury.groups.fury/fury (:count options)}
                                 :compute svc
                                 :timeout-ms 120000
                                 :timeout-val :timed-out!)]
    (if (= session :timed-out!)
      (die "timed out")
      (do
        #_(logging/infof "looks like %s has converged" session)
        (pallet/lift fury.groups.fury/fury
                     :compute svc
                     :timeout-ms 30000
                     :timeout-val :timed-out!
                     :phase (pallet/plan-fn
                             (logging/info "running hosts plan")
                             (hosts/add-hosts
                              (merge
                               hosts/localhost
                               hosts/ipv6-aliases
                               (make-hosts (compute/nodes svc))))
                             (hosts/hosts)))
        (Thread/sleep 120000)
        (logging/info "shutting down")
        (when (= :timed-out! (pallet/converge {fury.groups.fury/fury 0}
                                              :compute svc
                                              :timeout-ms 60000
                                              :timeout-val :timed-out!))
          (die "timed out"))))))

(defn main [opts]
  (logging/info opts)
  (build-and-run opts)
  (quit))

(defn -main [& args]
  (let [{:keys [options arguments summary errors]
         :as opts} (opts/parse args)]
    (if (pos? (count errors))
      (die errors)
      (main opts))))
