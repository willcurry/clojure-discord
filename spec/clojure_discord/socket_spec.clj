(ns clojure-discord.socket-spec
  (:require [speclj.core :refer :all]
            [gniazdo.core :as web-socket]
            [clojure-discord.socket :as socket]))

(describe "socket"
          (with-stubs)

          (it "calls send-msg on the websocket"
              (with-redefs-fn {#'web-socket/close (stub :close-socket)
                               #'web-socket/send-msg (stub :send-msg)}
                (fn [] (socket/heartbeat nil 1)))
                (should-have-invoked :send-msg))

          (it "closes web socket if no ACK response inbetween heartbeat"
              (with-redefs-fn {#'web-socket/close (stub :close-socket)
                               #'web-socket/send-msg (stub :send-msg)}
                (fn [] (socket/heartbeat nil 1) (socket/heartbeat nil 2)))
                (should-have-invoked :close-socket)))
