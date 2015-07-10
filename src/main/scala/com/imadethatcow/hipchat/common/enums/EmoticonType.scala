package com.imadethatcow.hipchat.common.enums

object EmoticonType extends Enumeration with Serializable {
  type EmoticonType = Value
  val global, group, all = Value
}
