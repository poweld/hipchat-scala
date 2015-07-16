package com.imadethatcow.hipchat.common.enums

import com.imadethatcow.hipchat.common.HipchatValueObject

object MessageFormat extends Enumeration with HipchatValueObject {
  type MessageFormat = Value
  val html, text = Value
}
