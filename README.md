hipchat-scala
=============

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.imadethatcow/hipchat-scala_2.11/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.imadethatcow/hipchat-scala_2.11)

Scala interface for the HipChat v2 API

Importing with sbt:
```
libraryDependencies += "com.imadethatcow" %% "hipchat-scala" % "<VERSION>" // version 1.2 is the latest as of July 2017
```

Please check [https://www.hipchat.com/docs/apiv2] to get a better sense for available capabilities.

Currently available capabilities and their (associated class):
* Emoticon retrieval (**Emoticons**)
* Private messaging (**PrivateMessenger**)
* Rooms retrieval (**Rooms**)
* Users retrieval (**Users**)
* Room history retrieval (**ViewHistory**)
* Webhook creation/deletion/retrieval (**Webhooks**)

TODO
----
* ISO-8601 date support in ViewHistory

Implemented Methods
===================

OAuth Sessions
--------------
* Oath delete session
* Oath get session
* Generate Token

Emoticons
---------
* Get all emoticons
* Get single emoticon

Rooms
-----
* Get all rooms
* Send room notification
* Set topic
* Create webhook
* Get all webhooks
* Delete webhook
* Get webhook
* View history
* Update room
* Create room
* Delete room

User
----
* Update photo
* Private message user
* Get all users

Unimplemented Methods
=====================

Add Ons
-------
* Get addon installable data

Capabilities
------------
* Get capabilities

Rooms
-----
* Send room notification redirect
* Get room statistics
* Get all members
* Add member
* Remove member
* Invite user

User
----
* Delete photo
* Update user
* Delete user
* View user
* Create user



Feel free to contact me with feature requests, comments, or bugs
