package com.projectz.module

import com.google.inject.Provides
import com.projectz.domain.{LoanRecord, User, UserID, UserInfo}
import com.projectz.repository.{CacheRepository, LoadRecordStore, LoanRecordRepository, MockLoanRecordRepository, OnMemoryCacheRepository}
import com.projectz.service.{LoanRecordServiceImpl, LoanService, ProductService, ProductServiceImpl}
import com.twitter.inject.TwitterModule
object TestModule extends TwitterModule {
  protected override def configure(): Unit = {
    super.configure()
    bind[ProductService].to[ProductServiceImpl]
    bind[LoanService].to[LoanRecordServiceImpl]
  }
  @Provides
  def providesLoanRecordRepository(): LoanRecordRepository = {
    new MockLoanRecordRepository()
  }
}
