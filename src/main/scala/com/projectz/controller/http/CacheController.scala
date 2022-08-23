package com.projectz.controller.http

import com.twitter.finatra.http.Controller
import com.projectz.domain.UserID
import com.projectz.domain.request.{GetCacheRequest, GetProductRequest, PutCacheRequest}
import com.projectz.service.UserCacheService

import javax.inject.Inject

/**
 * Created by SangDang on 9/16/16.
 */

class CacheController @Inject()  (userCacheService: UserCacheService)
  extends Controller {
  post("/addUser") { request: PutCacheRequest =>
    userCacheService.addUser(request.userID, request.userInfo)
    response.ok()
  }
   get("/getUser") { request: GetCacheRequest =>
    for {
      userInfo <- userCacheService.getUser(UserID(request.userID))
    } yield {
      response.ok(userInfo)
    }
  }

}
