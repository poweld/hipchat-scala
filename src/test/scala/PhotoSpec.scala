import com.imadethatcow.hipchat.users.Photos
import org.scalatest.{Matchers, FlatSpec}
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class PhotoSpec extends FlatSpec with Matchers with WithConfig {
  val apiToken = config("auth_token")
  val email = config("test_email")
  val image = config("test_image")

  val photo = new Photos(apiToken)
  "Put photo" should "return true" in {
    Await.result(photo.update(email, image), Duration.Inf) shouldEqual true
  }
  "Delete photo" should "return true" in {
    Await.result(photo.delete(email), Duration.Inf) shouldEqual true
  }
}
