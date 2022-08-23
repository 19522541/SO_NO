package com.projectz.module

import com.google.inject.Provides
import com.google.inject.name.Names
import com.twitter.inject.TwitterModule
import com.projectz.domain.{UserID, UserInfo}
import com.projectz.repository.{CacheRepository, OnMemoryCacheRepository}
import com.projectz.service.{UserCacheService, UserCacheServiceImpl}

import javax.inject.{Named, Singleton}

/**
 * Created by SangDang on 9/16/16.
 */
object MainModule extends TwitterModule {
  override def configure: Unit = {
    bind[UserCacheService].to[UserCacheServiceImpl]
  }

  @Singleton
  @Provides
  def providesUserCacheRepository(): CacheRepository[UserID, UserInfo] = {
    new OnMemoryCacheRepository[UserID, UserInfo]()
  }
}
