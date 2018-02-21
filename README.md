# clojure-discord

[![Clojars Project](https://img.shields.io/clojars/v/clojure-discord.svg)](https://clojars.org/clojure-discord)

## Setup

### Add the clojar to your project dependencies:

`:dependencies [[clojure-discord "0.1.0-SNAPSHOT"]]`

### Require necessary namespaces:

`:require [clojure-discord.gateway :as gateway]`

### Discord token:

Create a file called called config.edn within the root of your project which contains your bot token like so:

`{:token "your_bot_token"}`

### Connect to the gateway:

`(gateway/connect)`

**NOTE: You may not need to connect to the gateway, its up to you to check discord [docs](https://discordapp.com/developers/docs/intro)  and see if you require a connection or not**

### Start coding:

View examples [here](https://github.com/willcurry/clojure-discord/tree/master/example-bot).

## Functions and namespaces:

Namespace    | Function      | Arguments
------------ | ------------- | -------------
channel      | create-message| *string* channel-id, *string* message
