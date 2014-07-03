package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import org.slf4j.LoggerFactory

class AddOns() {
  def call(addonIdOrKey: Any,
           apiToken: String) = {
    var req = addToken(AddOns.url(addonIdOrKey, apiToken), apiToken)
    req = req.addQueryParameter("addon_id_or_key", addonIdOrKey.toString)
  }
}

object AddOns {
  val log = LoggerFactory.getLogger(getClass)
  def url(addonIdOrKey: Any, apiToken: String) = {
    addonIdOrKey match {
      case _: String | _: Long =>
        (apiUrlSecure / "addon" / addonIdOrKey.toString / "installable" / apiToken).GET
    }
  }
}
