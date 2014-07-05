package com.imadethatcow.hipchat.common.enums

object Scope extends Enumeration {
  type Scope = Value
  val send_notification, send_message, admin_room, view_group, admin_group, manage_rooms = Value
}
