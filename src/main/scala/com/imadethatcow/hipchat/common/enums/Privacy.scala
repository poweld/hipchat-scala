package com.imadethatcow.hipchat.common.enums

object Privacy extends Enumeration with Serializable {
  type Privacy = Value
  val public, `private` = Value
}
