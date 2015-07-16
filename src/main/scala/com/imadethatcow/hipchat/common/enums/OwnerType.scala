package com.imadethatcow.hipchat.common.enums

import com.imadethatcow.hipchat.common.HipchatValueObject

object OwnerType extends Enumeration with HipchatValueObject {
  type OwnerType = Value
  val client, user = Value
}
