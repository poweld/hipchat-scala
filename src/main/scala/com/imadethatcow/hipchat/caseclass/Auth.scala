package com.imadethatcow.hipchat.caseclass

import com.imadethatcow.hipchat.enums.AuthGrantType
import AuthGrantType.AuthGrantType

case class AuthRequest(grant_type: AuthGrantType,
                       username: Option[String] = None,
                       code: Option[String] = None,
                       redirect_url: Option[String] = None,
                       scope: Option[String] = None,
                       password: Option[String] = None,
                       refresh_token: Option[String] = None)
case class AuthResponse(access_token: String, expires_in: Long, group_name: String, token_type: String, scope: String, group_id: String)
