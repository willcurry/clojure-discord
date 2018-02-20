(ns clojure-discord.core-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.core :refer :all]))

(def ^:private ^:const base-url "https://discordapp.com/api/v6/")

(describe "discord"
          (with-stubs)

          (it "get-pinned-messages calls get-request"
              (with-redefs [get-request (stub :get-request)]
                (get-pinned-messages "fake id")
                (should-have-invoked :get-request {:with [(str base-url "channels/fake id/pins")]})))

           (it "get-channel calls get-request"
              (with-redefs [get-request (stub :get-request)]
                (get-channel "fake id")
                (should-have-invoked :get-request {:with [(str base-url "channels/fake id")]})))

            (it "get-channel-invites calls get-request"
              (with-redefs [get-request (stub :get-request)]
                (get-channel-invites "fake id")
                (should-have-invoked :get-request {:with [(str base-url "channels/fake id/invites")]})))

            (it "pin-message calls put-request"
              (with-redefs [put-request (stub :put-request)]
                (pin-message "channel-id" "message-id")
                (should-have-invoked :put-request {:with [(str base-url "channels/channel-id/pins/message-id")]})))

             (it "trigger-typing-indicator calls post-request"
              (with-redefs [post-request (stub :post-request)]
                (trigger-typing-indicator "channel-id")
                (should-have-invoked :post-request {:with [(str base-url "channels/channel-id/typing") ""]})))

             (it "create-message calls post-request"
              (with-redefs [post-request (stub :post-request)
                            current-time (fn [] 1)]
                (create-message "channel-id" "json")
                (should-have-invoked :post-request {:with [(str base-url "channels/channel-id/messages") "{\"content\":\"json\",\"nonce\":1,\"tts\":false}"]})))
            )
