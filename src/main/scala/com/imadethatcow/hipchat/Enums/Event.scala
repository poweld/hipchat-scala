package com.imadethatcow.hipchat.enums

object Event extends Enumeration {
  type Event = Value
  val room_message, room_notification, room_exit, room_enter, room_topic_change = Value
}
