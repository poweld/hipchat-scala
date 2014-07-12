hipchat-scala
=============

Scala interface for the HipChat v2 API

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
* Get all rooms (**Rooms**)
* Send room notification
* Create webhook
* Get all webhooks
* Delete webhook
* Get webhook
* View history
* Update room

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

Rooms
-----
* Create room
* Send room notification redirect
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
