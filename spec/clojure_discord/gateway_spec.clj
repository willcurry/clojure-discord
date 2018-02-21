(ns clojure-discord.gateway-spec
  (:require [speclj.core :refer :all]
            [clojure.data.json :as json]
            [clojure-discord.gateway :as gateway]
            [clojure-discord.parser :as parser]
            [clojure-discord.events :as events]
            [clojure-discord.requests :as request]
            [clojure-discord.socket :as socket]))

(describe "gateway"
          (with-stubs)

          (defn redefines []
            (with-redefs-fn {#'socket/create-connection (stub :create-connection)
                             #'request/get (stub :get-request)
                             #'socket/identify-with-discord (stub :identify)}
              gateway/connect))

          (it "gateway/connect calls create connection"
                (redefines)
                (should-have-invoked :create-connection))

          (it "gateway/connect calls get-request to find new gateway"
                (redefines)
                (should-have-invoked :get-request {:with ["https://discordapp.com/api/v6/gateway/bot"]}))

          (it "gateway/connect calls create identify-with-discord"
                (redefines)
                (should-have-invoked :identify))

          (it "handler function calls parser/parse"
              (let [handler-function (atom nil)]
              (with-redefs-fn {#'socket/create-connection (stub :create-connection)
                               #'socket/identify-with-discord (stub :identify)
                               #'request/get (stub :get-request)
                               #'socket/set-handler-function (fn [function] (reset! handler-function function))
                               #'parser/parse (stub :parse)}
                               (fn [] (gateway/connect) (@handler-function "fake-payload")))
                  (should-have-invoked :parse)))

          (def fake-json-payload
            (json/write-str {:op 0
                 :d "data"
                 :s 1
                 :t "READY"}))

          (it "handler function calls events/handle when op code is 0"
              (let [handler-function (atom nil)]
              (with-redefs-fn {#'socket/create-connection (stub :create-connection)
                               #'socket/identify-with-discord (stub :identify)
                               #'request/get (stub :get-request)
                               #'events/handle (stub :event-handle)
                               #'socket/set-handler-function (fn [function] (reset! handler-function function))}
                               (fn [] (gateway/connect) (@handler-function fake-json-payload)))
                  (should-have-invoked :event-handle))))
