(ns clojure-discord.requests-spec
  (:require [speclj.core :refer :all]
            [clojure-discord.requests :as request]))

(def ^:private ^:const base-url "https://discordapp.com/api/v6/")

(describe "requests"
          (it "prepare replaces question marks with args"
              (should= (str base-url "fake/id/url/id2") (request/prepare "fake/?/url/?" ["id" "id2"]))))
