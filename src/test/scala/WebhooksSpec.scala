import com.imadethatcow.hipchat._
import com.imadethatcow.hipchat.common.caseclass.Webhook
import com.imadethatcow.hipchat.common.enums.WebhookEvent
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

class WebhooksSpec extends FlatSpec {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")

  for (apiToken <- apiTokenTry; room <- testRoomTry) {
		val url = "http://www.imadethatcow.com"
    val webhooks = new Webhooks(apiToken)
    val event = WebhookEvent.room_message

    "Webhook create request" should "return a valid JSON response" in {
      for (hookResponse <- webhooks.create(room, url, event)) {
        val id = hookResponse.id

        val hookFut = webhooks.get(room, id)
        println(Await.result(hookFut, Duration.Inf))

        println(s"Deleting webhook id $id")
        val deleteFut = webhooks.delete(room, id)
        Await.result(deleteFut, Duration.Inf)
      }
    }

    "Webhook get all request" should "return a valid JSON response" in {
      val hooks = webhooks.getAll(room)
      println(Await.result(hooks, Duration.Inf))
    }
  }
}
