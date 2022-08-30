package com.projectz.module

import com.google.inject.Provides
import com.projectz.TestServer
import com.projectz.domain.{LoanRecord, User}
import com.projectz.repository.{JDBCClient, JDBCClientImpl, LoanRecordRepository, LoanRepositoryImpl}
import com.projectz.service.{LoanRecordServiceImpl, LoanService, UserService, UserServiceImpl}
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
    new JDBCClientImpl(url = "jdbc:mysql://localhost:3306/loanrecord", username = "root", pass = "di@2020!")
  }
  @Provides
  def providesUserThrift(): TUser.MethodPerEndpoint = {
    val methodPerEndpoint: TUser.MethodPerEndpoint = Thrift.client.build[TUser.MethodPerEndpoint]("localhost:8888")
    methodPerEndpoint
  }
}