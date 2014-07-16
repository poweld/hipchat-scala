package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.enums.WebhookEvent
import scala.concurrent.{ExecutionContext, Future}
import WebhookEvent._
import com.imadethatcow.hipchat.common.caseclass._
import ExecutionContext.Implicits.global

class Webhooks(private[this] val apiToken: String) extends Logging {
  def create(roomIdOrName: Any,
             url: String,
             event: WebhookEvent,
             pattern: Option[String] = None,
             name: Option[String] = None): Future[WebhookCreateResponse] = {
    val webhook = WebhookCreateRequest(url, event.toString, pattern, name)
    val body = mapper.writeValueAsString(webhook)
    val req = addToken(Webhooks.urlPost(roomIdOrName), apiToken)
      .setBody(body)
      .setHeader("Content-Type", "application/json")

    resolveAndDeserialize[WebhookCreateResponse](req, 201)
  }
  def get(roomIdOrName: Any,
          webhookId: Long): Future[Webhook] = {
    val req = addToken(Webhooks.urlGet(roomIdOrName, webhookId), apiToken)

    resolveAndDeserialize[WebhookGetItem](req) map {
      response => Webhook(response.room, response.url, response.pattern, response.event, response.name, response.id, response.creator)
    }
  }

  def getAll(roomIdOrName: Any,
             startIndex: Option[Long] = None,
             maxResults: Option[Long] = None): Future[Seq[WebhookSimple]] = {
    var req = addToken(Webhooks.urlGetAll(roomIdOrName), apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)

    resolveAndDeserialize[WebhookGetItems](req) map {
      response => response.items.map {
        item => WebhookSimple(item.url, item.pattern, item.event, item.name, item.id)
      }
    }
  }

  def delete(roomIdOrName: Any, webhookId: Long): Future[Boolean] = {
    val req = addToken(Webhooks.urlDelete(roomIdOrName, webhookId), apiToken)

    resolveRequest(req, 204) map { _ => true} recover { case _: Exception => false}
  }
}

object Webhooks {
  def urlBase(roomIdOrName: Any) = {
    roomIdOrName match {
      case _: String | _: Long =>
        apiUrl / "room" / roomIdOrName.toString / "webhook"
    }
  }
  def urlPost(roomIdOrName: Any) = urlBase(roomIdOrName).POST
  def urlGet(roomIdOrName: Any, webhookId: Long) = (urlBase(roomIdOrName) / webhookId).GET
  def urlGetAll(roomIdOrName: Any) = urlBase(roomIdOrName).GET
  def urlDelete(roomIdOrName: Any, webhookId: Long) = (urlBase(roomIdOrName) / webhookId).DELETE
}






































