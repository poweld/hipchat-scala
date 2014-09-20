import com.imadethatcow.hipchat._
import org.scalatest._
import scala.concurrent.ExecutionContext.Implicits.global

class AuthSpec extends FlatSpec with Matchers with WithConfig {
  val apiToken = config("auth_token")

  val auth = new Auth(apiToken)

  // this can work, but it is probably not good to test external API authentication
  /*
  "Auth create personal request" should "return a valid JSON response" in {
    for (authResponse <- auth.genPersonalToken()) {
      println(authResponse)
      authResponse.access_token should not equal apiTokenTry.get
    }
  }
  */
  /*
  "Auth create personal request" should "return a valid JSON response" in {
    val username = config("test_username")
    val password = config("test_password")

    val scopes = Seq(manage_rooms, admin_group, view_group, admin_room, send_message, send_notification)

    for (authResponse <- auth.genPasswordToken(username, password, scopes)) {
      println(authResponse)
    }
  }
  */
  "Auth get session" should "return a valid JSON response" in {
    for (sessionResponse <- auth.getSession(apiToken)) {
      println(sessionResponse)
    }
  }
  //"Auth delete session" should "return true" in {
  //  auth.deleteSession(apiToken) shouldEqual true
  //}
}
