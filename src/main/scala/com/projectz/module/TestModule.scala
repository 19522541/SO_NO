package com.projectz.module

import com.google.inject.Provides
import com.projectz.TestServer
import com.projectz.domain.{LoanRecord, User}
import com.projectz.repository.{JDBCClient, JDBCClientImpl, LoanRecordRepository, LoanRepositoryImpl}
import com.projectz.service.{LoanRecordServiceImpl, LoanService, UserService, UserServiceImpl}
import com.projectz.util.ZConfig
import com.twitter.finagle.{ListeningServer, Thrift, service}
import com.twitter.finatra.http.HttpServer
import com.twitter.finagle.thrift.{ThriftClient, ThriftClientRequest}
import com.twitter.inject.TwitterModule
import di.labs
import di.labs.service.TUser
import sun.net.www.http.HttpClient
object TestModule extends TwitterModule {
  protected override def configure(): Unit = {
    super.configure()
    bind[LoanService].to[LoanRecordServiceImpl]
    bind[LoanRecordRepository].to[LoanRepositoryImpl]
    bind[UserService].to[UserServiceImpl]
  }
  @Provides
  def providesJDBCClient(): JDBCClient = {
    new JDBCClientImpl(url = ZConfig.getString("database.mysql.url"), username = ZConfig.getString("database.mysql.username"), pass =ZConfig.getString("database.mysql.password"))
  }
  @Provides
  def providesUserThrift(): TUser.MethodPerEndpoint = {
    val methodPerEndpoint: TUser.MethodPerEndpoint = Thrift.client.build[TUser.MethodPerEndpoint]( ZConfig.getString("usersever.host"))
    methodPerEndpoint
  }
}