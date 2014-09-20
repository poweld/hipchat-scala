import com.imadethatcow.hipchat.users.Users
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class UsersSpec extends FlatSpec with WithConfig {
  val apiToken = config("auth_token")
  val room = config("test_room")

  val users = new Users(apiToken)

  "Users request" should "return a valid JSON response" in {
    for (seq <- users.getAll(); user <- seq) {
      println(user)
    }
  }

  it should "return a valid JSON response when specifying start-index" in {
    val fut = users.getAll(startIndex = Some(1L))
    Await.result(fut, Duration.Inf)
  }

  it should "return a valid JSON response when specifying max-results" in {
    val fut = users.getAll(maxResults = Some(1L))
    Await.result(fut, Duration.Inf)
  }

  it should "return a valid JSON response when specifying include-guests" in {
    val fut = users.getAll(includeGuests = Some(true))
    Await.result(fut, Duration.Inf)
  }

  // This will respond with a 403 if not using proper credentials
  // it should "return a valid JSON response when specifying include-deleted" in {
  //   users.getAll(includeDeleted = Some(true))
  // }
}
