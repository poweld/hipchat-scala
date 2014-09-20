package com.imadethatcow.hipchat.common.caseclass

case class Emoticon(url: String, id: Long, shortcut: String)
case class EmoticonsLinks(self: String, prev: String, next: String)
case class EmoticonsItemLinks(self: String)
case class EmoticonsItem(url: String, links: EmoticonsItemLinks, id: Long, shortcut: String)
case class EmoticonsResponse(items: Seq[EmoticonsItem], startIndex: Long, maxResults: Long, links: EmoticonsLinks)

case class EmoticonDetails(
  width:      Int,
  audio_path: Option[String],
  id:         Long,
  shortcut:   String,
  height:     Int,
  creator:    UsersItem,
  links:      EmoticonsItemLinks,
  url:        String
)
