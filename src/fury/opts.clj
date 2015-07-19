(ns fury.opts
  (:require [clojure.tools.cli :as cli]
            [clojure.edn :as edn]))

(def opts
  [[nil "--count NUMBER" "Number of Elasticsearch nodes"
    :parse-fn edn/read-string
    :validate [#(instance? Number %) "must be a number"]
    :default 1]
   [nil "--service SVC" "The compute provider"
    :default "virtualbox"]])

(defn parse [args]
  (cli/parse-opts args opts))
