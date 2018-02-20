(ns clojure-discord.parser
  (:require [clojure.data.json :as json]))

(defn parse [json-payload]
  (let [payload (json/read-str json-payload)]
    {:op (get payload "op")
     :data (get payload "d")
     :sequence-number (get payload "s")
     :event (get payload "t")}))
