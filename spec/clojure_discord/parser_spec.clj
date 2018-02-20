(ns clojure-discord.requests-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.parser :as parser]
            [clojure.data.json :as json]))

(def json-request
  (json/write-str {:op 0
                   :d "data"
                   :s 1
                   :t "READY"}))

(describe "parser"
          (it "should return hash map with correct values"
              (should= (parser/parse json-request) {:op 0
                                                    :data "data"
                                                    :sequence-number 1
                                                    :event "READY"})))
