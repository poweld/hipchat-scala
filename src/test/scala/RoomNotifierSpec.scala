import com.imadethatcow.hipchat.common.enums.{MessageFormat, Color}
import com.imadethatcow.hipchat.rooms.RoomNotifier
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class RoomNotifierSpec extends FlatSpec with WithConfig {
  val apiToken = config("auth_token")
  val room = config("test_room")

  val message = "This is an automated notification"
  val notifier = new RoomNotifier(apiToken)

  "Room notification" should "not fail" in {
    val fut = notifier.sendNotification(room, message)
    Await.result(fut, Duration.Inf)
  }

  it should "not fail when specifying a color" in {
    val fut = notifier.sendNotification(room, message, color = Color.random)
    Await.result(fut, Duration.Inf)
  }

  it should "not fail when specifying notify" in {
    val fut = notifier.sendNotification(room, message, notify = true)
    Await.result(fut, Duration.Inf)
  }

  it should "not fail when specifying message format" in {
    val fut = notifier.sendNotification(room, message, messageFormat = MessageFormat.text)
    Await.result(fut, Duration.Inf)
  }
}
