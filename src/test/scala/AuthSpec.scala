import com.imadethatcow.hipchat._
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.util.Try

class AuthSpec extends FlatSpec {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")

  // TODO: auth isn't completed
  // for (apiToken <- apiTokenTry; room <- testRoomTry) {
  //   val auth = new Auth(apiTokenTry.get)
  //   val grant = AuthGrantType.personal

  //   "Auth create request" should "return a valid JSON response" in {
  //     for (authResponse <- auth.call(grant)) {
  //       println(authResponse)
  //     }
  //   }
  // }
}
