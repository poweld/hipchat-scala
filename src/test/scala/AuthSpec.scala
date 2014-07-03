import com.imadethatcow.hipchat._
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.util.Try

class AuthSpec extends FlatSpec with Matchers {

  // this can work, but it is probably not good to test external API authentication

  /*
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")

  for (apiToken <- apiTokenTry) {
     val auth = new Auth(apiTokenTry.get)

     "Auth create personal request" should "return a valid JSON response" in {
       for (authResponse <- auth.generatePersonalToken()) {
         println(authResponse)
         authResponse.access_token should not equal apiTokenTry.get
       }
     }
  }
  */
}
