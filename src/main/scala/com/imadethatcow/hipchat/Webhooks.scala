package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.enums.WebhookEvent
import scala.concurrent.{ExecutionContext, Future}
import WebhookEvent._
import com.imadethatcow.hipchat.common.caseclass._

class Webhooks(private[this] val apiToken: String)(implicit executor: ExecutionContext) extends Logging {
  def create(
    roomIdOrName: String,
    url:          String,
    event:        WebhookEvent,
    pattern:      Option[String] = None,
    name:         Option[String] = None
  ): Future[WebhookCreateResponse] = {
    val webhook = WebhookCreateRequest(url, event.toString, pattern, name)
    val body = readMapper.writeValueAsString(webhook)
    val req = addToken(Webhooks.urlPost(roomIdOrName), apiToken)
      .setBody(body)
      .setHeader("Content-Type", "application/json")

    resolveAndDeserialize[WebhookCreateResponse](req, 201)
  }
  def get(
    roomIdOrName: String,
    webhookId:    Long
  ): Future[Webhook] = {
    val req = addToken(Webhooks.urlGet(roomIdOrName, webhookId), apiToken)

    resolveAndDeserialize[WebhookGetItem](req) map {
      response => Webhook(response.room, response.url, response.pattern, response.event, response.name, response.id, response.creator)
    }
  }

  def getAll(
    roomIdOrName: String,
    startIndex:   Option[Long] = None,
    maxResults:   Option[Long] = None
  ): Future[Seq[WebhookSimple]] = {
    var req = addToken(Webhooks.urlGetAll(roomIdOrName), apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)

    resolveAndDeserialize[WebhookGetItems](req) map {
      response =>
        response.items.map {
          item => WebhookSimple(item.url, item.pattern, item.event, item.name, item.id)
        }
    }
  }

  def delete(roomIdOrName: String, webhookId: Long): Future[Boolean] = {
    val req = addToken(Webhooks.urlDelete(roomIdOrName, webhookId), apiToken)

    resolveBoolRequest(req, 204)
  }
}

object Webhooks {
  private def urlBase(roomIdOrName: String) = apiUrl / "room" / roomIdOrName / "webhook"
  def urlPost(roomIdOrName: String) = urlBase(roomIdOrName).POST
  def urlGet(roomIdOrName: String, webhookId: Long) = (urlBase(roomIdOrName) / webhookId).GET
  def urlGetAll(roomIdOrName: String) = urlBase(roomIdOrName).GET
  def urlDelete(roomIdOrName: String, webhookId: Long) = (urlBase(roomIdOrName) / webhookId).DELETE
}
