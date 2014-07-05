import com.imadethatcow.hipchat._
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.util.Try
import com.imadethatcow.hipchat.common.enums.Scope._

class AuthSpec extends FlatSpec with Matchers {

  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val USERNAME = "com.imadethatcow.hipchat.username"
  val PASSWORD = "com.imadethatcow.hipchat.password"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")

  for (apiToken <- apiTokenTry) { //}; username <- usernameTry; password <- passwordTry) {
    val auth = new Auth(apiTokenTry.get)

    // this can work, but it is probably not good to test external API authentication

    /*
    "Auth create personal request" should "return a valid JSON response" in {
      for (authResponse <- auth.genPersonalToken()) {
        println(authResponse)
        authResponse.access_token should not equal apiTokenTry.get
      }
    */

    /*
    "Auth create personal request" should "return a valid JSON response" in {
      val usernameTry = Try(config.getString(USERNAME))
      val passwordTry = Try(config.getString(PASSWORD))
      if (usernameTry.isFailure) fail("Could not find username in config")
      if (passwordTry.isFailure) fail("Could not find password in config")

      val scopes = Seq(manage_rooms, admin_group, view_group, admin_room, send_message, send_notification)

      for (username <- usernameTry; password <- passwordTry) {
        for (authResponse <- auth.genPasswordToken(username, password, scopes)) {
          println(authResponse)
        }
      }
    }
    */
  }
}
