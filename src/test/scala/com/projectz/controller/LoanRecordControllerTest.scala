package com.projectz.controller

import com.projectz.TestServer
import com.projectz.domain.LoanRecord
import com.projectz.domain.response.{GetBorrowerResponse, LoanResponse}
import com.projectz.util.JsonUtils
import com.twitter.finagle.http.Response
import com.twitter.finagle.http.Status.Ok
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import org.yaml.snakeyaml.util.UriEncoder

class LoanRecordControllerTest extends FeatureTest{

  override protected val server = new EmbeddedHttpServer(
    twitterServer = new TestServer
  )
  test("[HTTP] get record of borrower"){
    val response: Response = server.httpGet(path ="/loan/detail?lender=9877e2f4-4be1-4713-b151-505aa0a712bb&borrower=2e85ff94-2724-11ed-8cd4-0242ac120002", andExpect = Ok)
    val lendResponses:Seq[LoanResponse] = JsonUtils.fromJson[Seq[LoanResponse]](response.contentString)
    println(lendResponses)
  }
  test("[HTTP] get  borrowers of lender by keyword"){
    //lender (Ng Van Z)  tim con co chu "Thi" trong ten =>>(NGuyen Thi X)
    val response= server.httpGet(path = "/loan?lender=9877e2f4-4be1-4713-b151-505aa0a712bb&keyword=Thi")
      val lendResponses:Seq[LoanResponse] = JsonUtils.fromJson[Seq[LoanResponse]](response.contentString)
    println(lendResponses)
  }
  test("[HTTP] get  all borrower of lender by set empty keyword field") {
    //lender (Ng Van Z)   =>>(NGuyen Thi X)&(Luong Huu )
    val response = server.httpGet(path = "/loan?lender=9877e2f4-4be1-4713-b151-505aa0a712bb&keyword=")
    val lendResponses: Seq[GetBorrowerResponse] = JsonUtils.fromJson[Seq[GetBorrowerResponse]](response.contentString)
   assert(lendResponses.length==2)
  }
  test("[HTTP] get borrower of lender when set lender is empty") {
    //lender (Ng Van Z)   =>>(NGuyen Thi X)&(Luong Huu )
    val response = server.httpGet(path = "/loan?lender=&keyword=")
    val lendResponses: Seq[LoanResponse] = JsonUtils.fromJson[Seq[LoanResponse]](response.contentString)
      assert(lendResponses.isEmpty)
  }
  test("[HTTP] Post add new  load record of lender"){
    val lenderId="3b5cc43e-2758-11ed-aa17-0242ac120002"
    val borrowerId="0afba88c-8254-4637-88dc-46f6902992af"
    val amount =1111
      val response =server.httpPost(path = s"/loan/${lenderId}",postBody =
      s"""
        |{
        |"borrower":"${borrowerId}",
        |"loan_reason":"Mua nha",
        |"loan_amount":${amount}
        |}
        |""".stripMargin,andExpect = Ok)
    val lendResponses:LoanRecord = JsonUtils.fromJson[LoanRecord](response.contentString)
  assert( lendResponses.lender==lenderId)
    assert(lendResponses.borrower==borrowerId)
    assert(lendResponses.loanAmount==1111)
  }

}
