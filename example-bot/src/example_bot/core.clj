(ns example-bot.core
  (:require [clojure-discord.core :as discord]
            [clojure-discord.channel :as channel]))

(defn -main
  [& args]
  (discord/connect)
  (channel/create-message "415199853845544971" "Hello!"))
