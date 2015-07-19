;;; Pallet project configuration file

(require
 '[fury.groups.fury :refer [fury]])

(defproject fury
  :provider {:jclouds
             {:node-spec
              {:image {:os-family :ubuntu :os-version-matches "12.04"
                       :os-64-bit true}}}}

  :groups [fury])
