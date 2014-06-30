hipchat-scala
=============

Scala interface for the HipChat v2 API

This documentation sucks
----------------------
At least for now. Please check [https://www.hipchat.com/docs/apiv2] to get a better sense for available capabilities.

Currently available capabilities and their (associated class):
* Emoticon retrieval (**Emoticons**)
* Private messaging (**PrivateMessenger**)
* Rooms retrieval (**Rooms**)
* Users retrieval (**Users**)
* Room history retrieval (**ViewHistory**)
* Webhook creation/deletion/retrieval (**Webhooks**)

TODO
----
* Auth
* Room attributes retrieval (members, topic, etc)
* ISO-8601 date support in ViewHistory

Implemented Methods
===================

Emoticons
---------
* Get all emoticons

Rooms
-----
* Get all rooms (**Rooms**)
* Send room notification
* Create webhook
* Get all webhooks
* Delete webhook
* Get webhook
* View history

User
----
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

Emoticons
---------
* Get single emoticon

OAuth Sessions
--------------
* Oath delete session
* Oath get session
* Generate Token

Rooms
-----
* Create room
* Send room notification redirect
* Update room
* Get room
* Delete room
* Get room statistics
* Get all members
* Set topic
* Add member
* Remove member
* Invite user

User
----
* Update photo
* Delete photo
* Update user
* Delete user
* View user
* Create user



Feel free to contact me with feature requests, comments, or bugs
