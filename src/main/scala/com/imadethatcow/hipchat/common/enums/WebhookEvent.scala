package com.imadethatcow.hipchat.common.enums

object WebhookEvent extends Enumeration {
  type WebhookEvent = Value
  val room_message, room_notification, room_exit, room_enter, room_topic_change = Value
}
