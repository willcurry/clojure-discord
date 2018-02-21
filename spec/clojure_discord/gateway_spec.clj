(ns clojure-discord.gateway-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.gateway :as gateway]
            [clojure-discord.socket :as socket]))

(describe "gateway"
          (with-stubs)

          (it "gateway/connect calls create connection"
              (with-redefs [socket/create-connection (stub :create-connection)
                            socket/identify-with-discord (stub :identify)]
                (gateway/connect)
                (should-have-invoked :create-connection)))

          (it "gateway/connect calls create identify-with-discord"
              (with-redefs [socket/create-connection (stub :create-connection)
                            socket/identify-with-discord (stub :identify)]
                (gateway/connect)
                (should-have-invoked :identify))))
