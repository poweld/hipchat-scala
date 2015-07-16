package com.imadethatcow.hipchat.common.enums

import com.imadethatcow.hipchat.common.HipchatValueObject

object Event extends Enumeration with HipchatValueObject {
  type Event = Value
  val room_message, room_notification, room_exit, room_enter, room_topic_change = Value
}
