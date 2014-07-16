import com.imadethatcow.hipchat.common.enums.{MessageFormat, Color}
import com.imadethatcow.hipchat._
import com.imadethatcow.hipchat.rooms.RoomNotifier
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

class RoomNotifierSpec extends FlatSpec {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")

  implicit def executionContext = ExecutionContext.Implicits.global

  for (apiToken <- apiTokenTry; room <- testRoomTry) {
    val message = "This is an automated notification"
    val notifier = new RoomNotifier(apiToken)

    "Room notification" should "not fail" in {
      val sentNotificationFut = notifier.sendNotification(room, message)
      sentNotificationFut.onComplete {
        case Success(successful) => assert(successful)
        case Failure(_) => assert(false)
      }
    }

    it should "not fail when specifying a color" in {
      val sentNotificationFut = notifier.sendNotification(room, message, color = Color.random)
      sentNotificationFut.onComplete {
        case Success(successful) => assert(successful)
        case Failure(_) => assert(false)
      }
    }

    it should "not fail when specifying notify" in {
      val sentNotificationFut = notifier.sendNotification(room, message, notify = true)
      sentNotificationFut.onComplete {
        case Success(successful) => assert(successful)
        case Failure(_) => assert(false)
      }
    }

    it should "not fail when specifying message format" in {
      val sentNotificationFut = notifier.sendNotification(room, message, messageFormat = MessageFormat.text)
      sentNotificationFut.onComplete {
        case Success(successful) => assert(successful)
        case Failure(_) => assert(false)
      }
    }
  }
}
