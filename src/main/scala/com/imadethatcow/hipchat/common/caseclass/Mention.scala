package com.imadethatcow.hipchat.common.caseclass

import com.imadethatcow.hipchat.common.HipchatValueObject

case class Mention(id: Long, mention_name: String, name: String) extends HipchatValueObject
