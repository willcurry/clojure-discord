(ns example-bot.core
  (:require [clojure-discord.core :as discord]))

(defn -main
  [& args]
  (print
    (discord/get-pinned-messages "415199853845544971")))
