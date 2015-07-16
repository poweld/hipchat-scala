package com.imadethatcow.hipchat.common.caseclass

import com.imadethatcow.hipchat.common.HipchatValueObject

case class HistoriesLinks(self: String, prev: String = "", next: String = "") extends HipchatValueObject
case class HistoriesResponse(items: Seq[HistoryItem], startIndex: Long, maxResults: Long, links: HistoriesLinks) extends HipchatValueObject
// TODO look into Jackson's @JsonIgnoreProperties annotation
// TODO kinda sucks that "from" seems to either return an object or a string :p
case class HistoryItem(color: String, date: String, file: HCFile, from: Any, id: String, mentions: Seq[MentionItem], message: String, message_format: String) extends HipchatValueObject
case class HistoryItemFromString(color: String, date: String, file: HCFile, from: String, id: String, mentions: Seq[MentionItem], message: String, message_format: String) extends HipchatValueObject
