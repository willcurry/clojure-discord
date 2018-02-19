(ns clojure-discord.core-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.core :as discord :refer :all]))

(def ^:private ^:const base-url "https://discordapp.com/api/v6/")

(describe "discord"
          (with-stubs)

          (it "call get request"
              (with-redefs [get-request (stub :get-request)]
                (get-pinned-messages "fake id")
                (should-have-invoked :get-request {:with [(str base-url "channels/fake id/pins")]}))))
