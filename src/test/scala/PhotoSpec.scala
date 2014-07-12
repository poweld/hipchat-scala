import com.imadethatcow.hipchat.users.Photo
import com.typesafe.config.ConfigFactory
import org.scalatest.{Matchers, FlatSpec}

import scala.util.Try

class PhotoSpec extends FlatSpec with Matchers {

  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val EMAIL_KEY = "com.imadethatcow.hipchat.test_email"
  val IMAGE_KEY = "com.imadethatcow.hipchat.test_image"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val emailTry = Try(config.getString(EMAIL_KEY))
  val imageTry = Try(config.getString(IMAGE_KEY))

  if (apiTokenTry.isFailure) fail(s"Could not find $API_TOKEN_KEY in config")
  if (emailTry.isFailure) fail(s"Could not find $EMAIL_KEY in config")
  if (imageTry.isFailure) fail(s"Could not find $IMAGE_KEY in config")

  for (apiToken <- apiTokenTry; email <- emailTry; image <- imageTry) {
    val photo = new Photo(apiToken)
    "Put photo" should "return a valid JSON response" in {
      for (sessionResponse <- photo.update(email, image)) {
        println(sessionResponse)
      }
    }
  }
}
