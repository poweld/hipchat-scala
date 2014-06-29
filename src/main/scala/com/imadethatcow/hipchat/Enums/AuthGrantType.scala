package com.imadethatcow.hipchat.enums

object AuthGrantType extends Enumeration {
  type AuthGrantType = Value
  val authorization_code, refresh_token, password, client_credentials, personal, room_notification = Value
}
