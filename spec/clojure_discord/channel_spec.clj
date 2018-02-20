(ns clojure-discord.channel-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.requests :as request]
            [clojure-discord.discord :as discord]
            [clojure-discord.channel :refer :all]))

(describe "channel"
          (with-stubs)

          (it "get-pinned-messages calls get-request"
              (with-redefs [request/get (stub :get)]
                (get-pinned-messages "fake id")
                (should-have-invoked :get {:with [(discord/add-base-url "channels/fake id/pins")]})))

           (it "get-channel calls get-request"
              (with-redefs [request/get (stub :get)]
                (get-channel "fake id")
                (should-have-invoked :get {:with [(discord/add-base-url "channels/fake id")]})))

            (it "get-channel-invites calls get-request"
              (with-redefs [request/get (stub :get)]
                (get-channel-invites "fake id")
                (should-have-invoked :get {:with [(discord/add-base-url "channels/fake id/invites")]})))

            (it "pin-message calls put-request"
              (with-redefs [request/put (stub :put)]
                (pin-message "channel-id" "message-id")
                (should-have-invoked :put {:with [(discord/add-base-url "channels/channel-id/pins/message-id")]})))

             (it "trigger-typing-indicator calls post-request"
              (with-redefs [request/post (stub :post)]
                (trigger-typing-indicator "channel-id")
                (should-have-invoked :post {:with [(discord/add-base-url "channels/channel-id/typing") ""]})))

             (it "create-message calls post-request"
              (with-redefs [request/post (stub :post)
                            current-time (fn [] 1)]
                (create-message "channel-id" "json")
                (should-have-invoked :post {:with [(discord/add-base-url "channels/channel-id/messages") "{\"content\":\"json\",\"nonce\":1,\"tts\":false}"]}))))
