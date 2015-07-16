package com.imadethatcow.hipchat.common.enums

import com.imadethatcow.hipchat.common.HipchatValueObject

object EmoticonType extends Enumeration with HipchatValueObject {
  type EmoticonType = Value
  val global, group, all = Value
}
