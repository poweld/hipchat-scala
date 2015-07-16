package com.imadethatcow.hipchat.common.enums

import com.imadethatcow.hipchat.common.HipchatValueObject

object Color extends Enumeration with HipchatValueObject {
  type Color = Value
  val yellow, red, green, purple, gray, random = Value
}
