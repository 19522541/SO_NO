package com.projectz.controller

import com.projectz.TestServer
import com.twitter.finagle.http.Status
import com.twitter.finagle.http.Status.Ok
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finatra.thrift.ThriftClient
import com.twitter.inject.server.{EmbeddedTwitterServer, FeatureTest}

class ProductControllerTest extends  FeatureTest{

  override protected val server = new EmbeddedHttpServer(
    twitterServer = new TestServer
  ) with ThriftClient

  test("[HTTP] Put product successfully") {

    server.httpPost(path = "/createProduct", postBody = """
            {
              "product_id":{
                "id":"1"
              },
              "product_detail":{
                "product_id":{
                  "id":"1"
                },
                "product_name":"test_1",
                "manufacturer":"Test___1"

              }
            }
          """.stripMargin, andExpect = Ok)
  }

//  test("Test get product Http") {
//    server.httpGet(path = "/getProduct",andExpect = Status.Ok)
//
//  }
}
