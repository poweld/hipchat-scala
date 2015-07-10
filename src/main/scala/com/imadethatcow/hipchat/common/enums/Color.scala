package com.imadethatcow.hipchat.common.enums

object Color extends Enumeration with Serializable {
  type Color = Value
  val yellow, red, green, purple, gray, random = Value
}
