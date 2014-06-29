package com.imadethatcow.hipchat.enums

object Scopes extends Enumeration {
  type Scopes = Value
  val send_notification, send_message, admin_room, view_group, admin_group, manage_rooms = Value
}
