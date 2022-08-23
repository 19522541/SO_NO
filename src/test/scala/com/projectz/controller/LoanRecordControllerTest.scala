package com.projectz.controller

import com.projectz.TestServer
import com.twitter.finagle.http.Status.Ok
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finatra.thrift.ThriftClient
import com.twitter.inject.server.FeatureTest

class LoanRecordControllerTest extends FeatureTest{

  override protected val server = new EmbeddedHttpServer(
    twitterServer = new TestServer
  ) with ThriftClient
  test("[HTTP] Put add record successfully"){
    server.httpPost(path = "/loan", postBody = """
            {
                "id":"0001",
                "name":"Vuong"
            }
          """.stripMargin, andExpect = Ok)
  }

}
