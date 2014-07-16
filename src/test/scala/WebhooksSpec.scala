import com.imadethatcow.hipchat._
import com.imadethatcow.hipchat.common.enums.WebhookEvent
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

class WebhooksSpec extends FlatSpec {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")

  implicit def executionContext = ExecutionContext.Implicits.global

  for (apiToken <- apiTokenTry; room <- testRoomTry) {
    val url = "http://www.imadethatcow.com"
    val webhooks = new Webhooks(apiToken)
    val event = WebhookEvent.room_message

    "Webhook create request" should "return a valid JSON response" in {
      for (hookResponse <- webhooks.create(room, url, event)) {
        val id = hookResponse.id

        webhooks.get(room, id).onComplete {
          case Success(hook) => println(hook)
          case Failure(_) => fail("Did not receive a valid hook from get request")
        }

        println(s"Deleting webhook id $id")

        webhooks.delete(room, id).onComplete {
          case Success(successful) => assert(successful)
          case Failure(_) => fail("Did not delete the webhook")
        }
      }
    }

    "Webhook get all request" should "return a valid JSON response" in {
      webhooks.getAll(room).onComplete {
        case Success(hooks) =>
          for (hook <- hooks)
            println(hook)
        case Failure(_) => fail("Did not receive a valid hook from get request")
      }
    }
  }
}
