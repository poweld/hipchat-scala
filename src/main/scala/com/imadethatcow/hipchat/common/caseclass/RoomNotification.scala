package com.imadethatcow.hipchat.common.caseclass

import com.imadethatcow.hipchat.common.HipchatValueObject

case class RoomNotification(
  color:          String,
  message:        String,
  _notify:        Boolean,
  message_format: String
) extends HipchatValueObject
