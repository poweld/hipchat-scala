package com.imadethatcow.hipchat.common.caseclass

import com.imadethatcow.hipchat.common.HipchatValueObject

case class Webhook(room: Option[RoomsItem], url: String, pattern: Option[String], event: String, name: String, id: Long, creator: Option[UsersItem]) extends HipchatValueObject
case class WebhookSimple(url: String, pattern: Option[String], event: String, name: String, id: Long) extends HipchatValueObject

case class WebhookCreateRequest(url: String, event: String, pattern: Option[String] = None, name: Option[String] = None) extends HipchatValueObject
case class WebhookItemLinks(self: String) extends HipchatValueObject
case class WebhookCreateResponse(id: Long, links: WebhookItemLinks) extends HipchatValueObject

case class WebhookGetRequest(room_id_or_name: String, webhook_id: Long) extends HipchatValueObject
case class WebhookGetItem(room: Option[RoomsItem], links: WebhookItemLinks, creator: Option[UsersItem], url: String, pattern: Option[String], created: String, event: String, name: String, id: Long) extends HipchatValueObject

case class WebhookGetSimpleItem(links: WebhookItemLinks, url: String, pattern: Option[String], event: String, name: String, id: Long) extends HipchatValueObject
case class WebhookItemsLinks(self: String, prev: String, next: String) extends HipchatValueObject
case class WebhookGetItems(items: Seq[WebhookGetSimpleItem], startIndex: Long, maxResults: Long, links: WebhookItemsLinks) extends HipchatValueObject

// The following are all for webhook callbacks, starting with the common classes
case class WebhookRoomLinks(members: Option[String], self: String, webhooks: String) extends HipchatValueObject
case class WebhookRoom(id: Long, links: WebhookRoomLinks, name: String) extends HipchatValueObject
case class WebhookSenderLinks(self: String) extends HipchatValueObject
case class WebhookSender(id: Long, links: WebhookSenderLinks, mention_name: String, name: String) extends HipchatValueObject

case class WebhookRoomEnterItem(room: WebhookRoom, sender: WebhookSender) extends HipchatValueObject
case class WebhookRoomEnter(event: String, item: WebhookRoomEnterItem, oauth_client_id: Option[String], webhook_id: Long) extends HipchatValueObject

case class WebhookRoomExitItem(room: WebhookRoom, sender: WebhookSender) extends HipchatValueObject
case class WebhookRoomExit(event: String, item: WebhookRoomExitItem, oauth_client_id: Option[String], webhook_id: Long) extends HipchatValueObject

case class WebhookMessage(date: String, file: Option[HCFile], from: From, id: String, mentions: Option[Seq[MentionItem]], message: String) extends HipchatValueObject
case class WebhookRoomMessageItem(message: WebhookMessage, room: Option[RoomsItem]) extends HipchatValueObject
case class WebhookRoomMessage(event: String, item: WebhookRoomMessageItem, oauth_client_id: Option[String], webhook_id: Long) extends HipchatValueObject

case class WebhookRoomNotificationMessage(color: Option[String], date: String, from: Option[String], id: Long, mentions: Seq[Mention], message: String, message_format: String) extends HipchatValueObject
case class WebhookRoomNotificationItem(message: WebhookRoomNotificationMessage, room: WebhookRoom) extends HipchatValueObject
case class WebhookRoomNotification(event: String, item: WebhookRoomNotificationItem, oauth_client_id: Option[String], webhook_id: String) extends HipchatValueObject

case class WebhookRoomTopicChangeItem(room: WebhookRoom, sender: WebhookSender, topic: String) extends HipchatValueObject
case class WebhookRoomTopicChange(event: String, item: WebhookRoomTopicChangeItem, oauth_client_id: Option[String], webhook_id: String) extends HipchatValueObject
