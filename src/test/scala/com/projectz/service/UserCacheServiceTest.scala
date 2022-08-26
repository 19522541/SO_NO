package com.projectz.service

import com.google.inject.Guice
import com.twitter.inject.{Injector, IntegrationTest}
import com.projectz.domain.{UserID, UserInfo}
import com.projectz.module.MainModule
import com.projectz.util.Implicits.FutureEnhance

class UserCacheServiceTest extends IntegrationTest {


  override protected def injector: Injector = Injector(Guice.createInjector(Seq(MainModule): _*))

  private val service = injector.instance[UserCacheService]
  test(" Put cache successfull") {
    service.addUser(UserID("1"), UserInfo(
      UserID("1"),
      "test_1",
      99,
      "male"
    ))
  }


  test("be able to get back") {
    val userInfo = service.getUser(UserID("1")).sync()

    assert(userInfo != null)
  }
}


