(ns fury.vmfest
  (:require [pallet.api :as pallet]
            [pallet.configure :as conf]
            [pallet.compute :as compute]))

(defn get-service []
  (pallet/compute-service "vbox"))
