package com.projectz.controller.thrift

import com.twitter.finatra.thrift.Controller
import com.twitter.util.Future
import com.projectz.domain.ThriftImplicit.{Future2T, T2UserId, T2UserInfo}
import com.projectz.service.TUserCacheService.{AddUser, GetUser, Ping}
import com.projectz.service.{TUserCacheService, UserCacheService}

import javax.inject.{Inject, Singleton}

@Singleton
class CacheController @Inject()(cacheService: UserCacheService)
  extends Controller(TUserCacheService) {
  handle(Ping) { request: Ping.Args =>
    Future.value("pong")
  }

  handle(AddUser) { request: AddUser.Args =>
  {
    cacheService.addUser(request.userInfo.userId, request.userInfo)
    Future.Unit
  }

  }
  handle(GetUser) { request: GetUser.Args =>
    {
      cacheService.getUser(request.userId)
    }
  }
}
