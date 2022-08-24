package com.projectz.controller

import com.projectz.TestServer
import com.projectz.domain.LoanRecord
import com.projectz.domain.response.LoanResponse
import com.projectz.util.JsonUtils
import com.twitter.finagle.http.Response
import com.twitter.finagle.http.Status.Ok
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finatra.thrift.ThriftClient
import com.twitter.inject.server.FeatureTest

class LoanRecordControllerTest extends FeatureTest{

  override protected val server = new EmbeddedHttpServer(
    twitterServer = new TestServer
  ) with ThriftClient
  test("[HTTP] get record of borrower"){
    val response: Response = server.httpGet(path = "/loan/detail?lender_id=001&borrower_id=002", andExpect = Ok)
    val lendResponses:Seq[LoanResponse] = JsonUtils.fromJson[Seq[LoanResponse]](response.contentString)
    assert(lendResponses.size == 1)
  }
  test("[HTTP] get  borrowers of lender by keyword"){
    val response= server.httpGet(path = "/loan?lender_id=001&keyword=HUU", andExpect = Ok)
      val lendResponses:Seq[LoanResponse] = JsonUtils.fromJson[Seq[LoanResponse]](response.contentString)
      assert(lendResponses.size==2)
  }
  test("[HTTP] Post add new  load record of lender"){
    val response =server.httpPost(path = "/loan/001",postBody =
      """
        |{
        |"borrower_id":"001",
        |"loan_reason":"Mua nha",
        |"loan_amount":1111000
        |}
        |""".stripMargin,andExpect = Ok)
    val lendResponses:LoanRecord = JsonUtils.fromJson[LoanRecord](response.contentString)
    assert(lendResponses.lenderId=="001")
  }
}
