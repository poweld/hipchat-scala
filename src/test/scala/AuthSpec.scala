import com.imadethatcow.hipchat._
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.util.Try

class AuthSpec extends FlatSpec with Matchers {

  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val USERNAME = "com.imadethatcow.hipchat.username"
  val PASSWORD = "com.imadethatcow.hipchat.password"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val usernameTry = Try(config.getString(USERNAME))
  val passwordTry = Try(config.getString(PASSWORD))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (usernameTry.isFailure) fail("Could not find username in config")
  if (passwordTry.isFailure) fail("Could not find password in config")

  for (apiToken <- apiTokenTry; username <- usernameTry; password <- passwordTry) {
    val auth = new Auth(apiTokenTry.get)

    // this can work, but it is probably not good to test external API authentication

//    "Auth create personal request" should "return a valid JSON response" in {
//      for (authResponse <- auth.generatePersonalToken()) {
//        println(authResponse)
//        authResponse.access_token should not equal apiTokenTry.get
//      }

    "Auth create personal request" should "return a valid JSON response" in {
      for (authResponse <- auth.generatePasswordToken(username, password)) {
        println(authResponse)
      }
    }
  }
}
