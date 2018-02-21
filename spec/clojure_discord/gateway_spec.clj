(ns clojure-discord.gateway-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.gateway :as gateway]
            [clojure-discord.parser :as parser]
            [clojure-discord.requests :as request]
            [clojure-discord.socket :as socket]))

(describe "gateway"
          (with-stubs)

          (it "gateway/connect calls create connection"
              (with-redefs-fn {#'socket/create-connection (stub :create-connection)
                               #'request/get (stub :get-request)
                               #'socket/identify-with-discord (stub :identify)}
                               gateway/connect)
                (should-have-invoked :create-connection))

          (it "gateway/connect calls get-request to find new gateway"
              (with-redefs-fn {#'socket/create-connection (stub :create-connection)
                               #'request/get (stub :get-request)
                               #'socket/identify-with-discord (stub :identify)}
                               gateway/connect)
                (should-have-invoked :get-request {:with ["https://discordapp.com/api/v6/gateway/bot"]}))

          (it "gateway/connect calls create identify-with-discord"
              (with-redefs-fn {#'socket/create-connection (stub :create-connection)
                               #'request/get (stub :get-request)
                               #'socket/identify-with-discord (stub :identify)}
                               gateway/connect)
                (should-have-invoked :identify))

          (it "handler function calls parser/parse"
              (let [handler-function (atom nil)]
              (with-redefs-fn {#'socket/create-connection (stub :create-connection)
                               #'socket/identify-with-discord (stub :identify)
                               #'request/get (stub :get-request)
                               #'socket/set-handler-function (fn [function] (reset! handler-function function))
                               #'parser/parse (stub :parse)}
                               (fn [] (gateway/connect) (@handler-function "fake-payload")))
                  (should-have-invoked :parse))))
