(ns example-bot.core
  (:require [clojure-discord.gateway :as gateway]
            [clojure-discord.channel :as channel]))

(defn -main
  [& args]
  (gateway/connect)
  (channel/create-message "415199853845544971" "Hello!"))
