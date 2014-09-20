import com.imadethatcow.hipchat.users.PrivateMessenger
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class PrivateMessengerSpec extends FlatSpec with Matchers with WithConfig {
  val apiToken = config("auth_token")
  val room = config("test_room")
  val email = config("test_email")

  val message = "This is an automated private message"
  val messenger = new PrivateMessenger(apiToken)

  "Private message" should "not fail" in {
    val fut = messenger.sendMessage(email, message)
    Await.result(fut, Duration.Inf) shouldEqual true
  }
}
