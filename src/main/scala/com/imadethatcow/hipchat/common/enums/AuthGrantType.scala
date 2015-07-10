package com.imadethatcow.hipchat.common.enums

object AuthGrantType extends Enumeration with Serializable {
  type AuthGrantType = Value
  val authorization_code, refresh_token, password, client_credentials, personal, room_notification = Value
}
