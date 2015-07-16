package com.imadethatcow.hipchat.common.enums

import com.imadethatcow.hipchat.common.HipchatValueObject

object AuthGrantType extends Enumeration with HipchatValueObject {
  type AuthGrantType = Value
  val authorization_code, refresh_token, password, client_credentials, personal, room_notification = Value
}
