package com.imadethatcow.hipchat.common.enums

import com.imadethatcow.hipchat.common.HipchatValueObject

object Privacy extends Enumeration with HipchatValueObject {
  type Privacy = Value
  val public, `private` = Value
}
