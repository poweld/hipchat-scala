import com.imadethatcow.hipchat.users.Photos
import com.typesafe.config.ConfigFactory
import org.scalatest.{Matchers, FlatSpec}
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

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
    val photo = new Photos(apiToken)
    "Put photo" should "return true" in {
      Await.result(photo.update(email, image), Duration.Inf) shouldEqual true
    }
    "Delete photo" should "return true" in {
      Await.result(photo.delete(email), Duration.Inf) shouldEqual true
    }
  }
}
