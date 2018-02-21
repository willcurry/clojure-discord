(ns clojure-discord.events-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.socket :as socket]
            [clojure-discord.events :as events]))

(describe "events"
          (it "READY event updates session-id"
                (events/handle {:op 0
                                :event "READY"
                                :sequence-number 1
                                :data {"session_id" "fake id"}})
                (should= "fake id" (events/get-session-id))))

