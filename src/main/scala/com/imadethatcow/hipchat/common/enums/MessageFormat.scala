package com.imadethatcow.hipchat.common.enums

object MessageFormat extends Enumeration with Serializable {
  type MessageFormat = Value
  val html, text = Value
}
