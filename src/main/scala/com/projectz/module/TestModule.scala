package com.projectz.module

import com.google.inject.Provides
import com.projectz.domain.{LoanRecord, User, UserID, UserInfo}
import com.projectz.repository.{CacheRepository, JDBCClient, JDBCClientImpl, JDBCConnectObject, LoanRecordRepository, LoanRepositoryImpl, OnMemoryCacheRepository}
import com.projectz.service.{LoanRecordServiceImpl, LoanService, ProductService, ProductServiceImpl}
import com.twitter.inject.TwitterModule
object TestModule extends TwitterModule {
  protected override def configure(): Unit = {
    super.configure()
    bind[ProductService].to[ProductServiceImpl]
    bind[LoanService].to[LoanRecordServiceImpl]
    bind[LoanRecordRepository].to[LoanRepositoryImpl]

  }

  @Provides
  def providesJDBCClient(): JDBCClient = {
    new JDBCClientImpl(url = "jdbc:mysql://localhost:3306/loanrecord", username = "root", pass = "di@2020!")
  }
}