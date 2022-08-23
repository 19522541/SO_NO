package com.projectz.module

import com.google.inject.Provides
import com.projectz.domain.{LoanRecord, RecordModifier, User, UserID, UserInfo}
import com.projectz.repository.{CacheRepository, LoadRecordStore, LoanRecordRepository, OnMemoryCacheRepository}
import com.projectz.service.{LoanRecordService, LoanRecordServiceImpl, ProductService, ProductServiceImpl}
import com.twitter.inject.TwitterModule
object TestModule extends TwitterModule {
  protected override def configure(): Unit = {
    super.configure()
    bind[ProductService].to[ProductServiceImpl]
    bind[LoanRecordService].to[LoanRecordServiceImpl]
  }
  @Provides
  def providesLoanRecordRepository(): LoanRecordRepository[String, LoanRecord,RecordModifier] = {
    new LoadRecordStore()
  }
}