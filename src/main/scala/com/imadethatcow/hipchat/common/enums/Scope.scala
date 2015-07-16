package com.imadethatcow.hipchat.common.enums

import com.imadethatcow.hipchat.common.HipchatValueObject

object Scope extends Enumeration with HipchatValueObject {
  type Scope = Value
  val send_notification, send_message, admin_room, view_group, admin_group, manage_rooms = Value
}
