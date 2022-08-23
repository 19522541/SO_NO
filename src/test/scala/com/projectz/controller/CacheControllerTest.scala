package com.projectz.controller

import com.twitter.finagle.Thrift
import com.twitter.finagle.http.Status
import com.twitter.finagle.http.Status.Ok
import com.twitter.finagle.service.{Backoff, RetryBudget}
import com.twitter.finagle.thrift.ClientId
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finatra.thrift.ThriftClient
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future
import com.projectz.TestServer
import com.projectz.domain.thrift.{TUserID, TUserInfo}
import com.projectz.service.TUserCacheService
import org.scalatest.Assertions

/**
 * Created by SangDang on 9/18/16.
 */
class CacheControllerTest extends FeatureTest {
  override protected val server = new EmbeddedHttpServer(
    twitterServer = new TestServer
  ) with ThriftClient

  test("[HTTP] Put cache successfull") {

    server.httpPost(path = "/addUser", postBody = """
            {
              "user_id":{
                "id":"1"
              },
              "user_info":{
                "user_id":{
                  "id":"1"
                },
                "user_name":"test_1",
                "age":99,
                "sex":"male"
              }
            }
          """.stripMargin, andExpect = Ok)
  }

  test("be able to get back") {
    server.httpGet(
      path = "/getUser?user_id=1",
      andExpect = Status.Ok,
      withJsonBody = """
            {
             "user_id": {
               "id": "1"
             },
             "user_name": "test_1",
             "age": 99,
             "sex": "male"
           }
          """.stripMargin
    )
  }

  test("[Thrift] put cache successful ") {
    lazy val client =
      server.thriftClient[TUserCacheService[Future]](clientId = "1")
    client.addUser(TUserInfo(TUserID("101"), "test", 100, "male"))
    client
      .getUser(TUserID("101"))
      .onSuccess(userInfo => {
        Assertions.assert(userInfo.userId.equals("101"))
        Assertions.assert(userInfo.username.equals("test"))
        Assertions.assert(userInfo.age.equals(100))
        Assertions.assert(userInfo.sex.equals("male"))
      })
      .onFailure(fn => throw fn)
  }

  test("[Thrift] external put cache successful") {
    import com.twitter.util.Duration

    val host = "localhost"
    val port = server.thriftExternalPort
    val timeoutInSecs = 15
    val label = "client"
    val client = Thrift.client
      .withRequestTimeout(Duration.fromSeconds(timeoutInSecs))
      .withSessionPool
      .minSize(1)
      .withSessionPool
      .maxSize(10)
      .withRetryBudget(RetryBudget())
      .withRetryBackoff(
        Backoff.exponentialJittered(
          Duration.fromSeconds(5),
          Duration.fromSeconds(32)
        )
      )
      .withClientId(ClientId(label))
      .build[TUserCacheService.MethodPerEndpoint](s"$host:$port", label)

    client.addUser(TUserInfo(TUserID("111"), "t_test", 101, "female"))
    client
      .getUser(TUserID("111"))
      .onSuccess(userInfo => {
        Assertions.assert(userInfo.userId.equals("111"))
        Assertions.assert(userInfo.username.equals("t_test"))
        Assertions.assert(userInfo.age.equals(101))
        Assertions.assert(userInfo.sex.equals("female"))
      })
      .onFailure(fn => throw fn)
  }

}
