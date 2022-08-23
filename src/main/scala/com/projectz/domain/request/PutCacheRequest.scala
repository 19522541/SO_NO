package com.projectz.domain.request

import com.projectz.domain.{UserID, UserInfo}

case class PutCacheRequest(userID: UserID, userInfo: UserInfo) {}
