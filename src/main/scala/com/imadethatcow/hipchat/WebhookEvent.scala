package com.imadethatcow.hipchat

object WebhookEvent extends Enumeration {
  type WebhookEvent = Value
  val room_message, room_notification, room_exit, room_enter, room_topic_change = Value
}
