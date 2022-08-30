package com.projectz.module

import com.google.inject.Provides
import com.google.inject.name.Names
import com.twitter.inject.TwitterModule
import com.projectz.repository.{ JDBCClient, JDBCClientImpl, LoanRecordRepository, LoanRepositoryImpl}
import com.projectz.service.{LoanRecordServiceImpl, LoanService }
import com.projectz.util.ZConfig
import sun.util.resources.cldr.ca.TimeZoneNames_ca

import javax.inject.{Named, Singleton}

/**
 * Created by SangDang on 9/16/16.
 */
object MainModule extends TwitterModule {
  protected override def configure(): Unit = {
    super.configure()
    bind[LoanService].to[LoanRecordServiceImpl]
    bind[LoanRecordRepository].to[LoanRepositoryImpl]
  }
  @Singleton
  @Provides
  def providesJDBCClient(): JDBCClient = {
    new JDBCClientImpl(url =ZConfig.getString("database.mysql.url"), username = ZConfig.getString("database.mysql.username"), pass = ZConfig.getString("database.mysql.password"))
  }

}
