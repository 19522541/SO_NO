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
import org.yaml.snakeyaml.util.UriEncoder

class LoanRecordControllerTest extends FeatureTest{

  override protected val server = new EmbeddedHttpServer(
    twitterServer = new TestServer
  ) with ThriftClient
  test("[HTTP] get record of borrower"){

    val response: Response = server.httpGet(path ="/loan/detail?lender=VUong&borrower=LUOGN", andExpect = Ok)
    val lendResponses:Seq[LoanResponse] = JsonUtils.fromJson[Seq[LoanResponse]](response.contentString)
  }
  test("[HTTP] get  borrowers of lender by keyword"){
    val response= server.httpGet(path = "/loan?lender=VUong&keyword=VUong")
      val lendResponses:Seq[LoanResponse] = JsonUtils.fromJson[Seq[LoanResponse]](response.contentString)

  }
  test("[HTTP] Post add new  load record of lender"){
    val response =server.httpPost(path = "/loan/HUUVUong",postBody =
      """
        |{
        |"borrower":"001",
        |"loan_reason":"Mua nha",
        |"loan_amount":1111000
        |}
        |""".stripMargin,andExpect = Ok)
    val lendResponses:LoanRecord = JsonUtils.fromJson[LoanRecord](response.contentString)

  }
}
