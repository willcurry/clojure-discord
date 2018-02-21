(ns clojure-discord.socket-spec
  (:require [speclj.core :refer :all]
            [gniazdo.core :as web-socket]
            [clojure-discord.socket :as socket]))

(describe "socket"
          (with-stubs)

          (it "calls send-msg on the websocket"
              (socket/update-ack true)
              (with-redefs-fn {#'web-socket/close (stub :close-socket)
                               #'web-socket/send-msg (stub :send-msg)}
                (fn [] (socket/heartbeat nil 1)))
                (should-have-invoked :send-msg))

          (it "closes web socket if no ACK response inbetween heartbeat"
              (socket/update-ack true)
              (with-redefs-fn {#'web-socket/close (stub :close-socket)
                               #'web-socket/send-msg (stub :send-msg)}
                (fn [] (socket/heartbeat nil 1) (socket/heartbeat nil 2)))
                (should-have-invoked :close-socket))

          (it "responds with alive if alive"
              (socket/update-ack true)
              (with-redefs [web-socket/close (fn [connection])
                            web-socket/send-msg (fn [connection msg])]
                (should= "alive" (socket/heartbeat nil 1))))

          (it "responds with died if died"
              (socket/update-ack true)
              (with-redefs [web-socket/close (fn [connection])
                            web-socket/send-msg (fn [connection msg])]
                (socket/heartbeat nil 1)
                (should= "died" (socket/heartbeat nil 2))))

          (it "resume calls web-socket/send-msg"
              (with-redefs-fn {#'web-socket/close (stub :close-socket)
                               #'web-socket/send-msg (stub :send-msg)}
                (fn [] (socket/resume nil "fake id" "123" "token")))
                (should-have-invoked :send-msg {:with [nil "{\"token\":\"token\",\"session_id\":\"fake id\",\"seq\":\"123\"}"]})))
