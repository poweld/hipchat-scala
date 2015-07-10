package com.imadethatcow.hipchat.common.enums

object OwnerType extends Enumeration with Serializable {
  type OwnerType = Value
  val client, user = Value
}
