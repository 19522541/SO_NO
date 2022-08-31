package com.projectz.service

import com.google.inject.Guice
import com.projectz.domain.LoanRecord
import com.projectz.domain.request.AddLoanRecordRequest
import com.projectz.module.TestModule
import com.projectz.util.Implicits.FutureEnhance
import com.twitter.inject.{Injector, IntegrationTest}

import java.util.Date

class LoanRecordServiceTest extends IntegrationTest{
  override protected def injector: Injector = Injector(Guice.createInjector(Seq(TestModule):_*))
  private val service = injector.instance[LoanService]
  test( "CREATE new LoanRecord"){
    val addRequest:AddLoanRecordRequest=AddLoanRecordRequest(
      loanAmount = 10000,
      borrower = "LUOGN ",
      lender = "VUong",
      loanReason = "MUON MUA NHA")
   val createdResult= service.addRecord(addRequest)
    assert(createdResult.lender==addRequest.lender)
  }
  test( "Get borrowers of lender"){
    val borrowers= service.getBorrowers("LUONG HUU VUong","ng Hữu Vư")
    assert(borrowers.size==1)
  }
  test( "Get borrowers of lender empty keyword"){
    val borrowers= service.getBorrowers("9877e2f4-4be1-4713-b151-505aa0a712bb","")
    assert(borrowers.size==1)
  }
  test( "Get borrowers of anonymous lender"){
    val borrowers= service.getBorrowers("","")
    assert(borrowers.size==0)
  }
  test( "Loan Record of Borrower" ){
    val loadRecord = service.getLoanDetails(lender = "LUONG HUU VUong",borrower = "Lương Hữu Vương")
    assert(loadRecord.size==1 )
  }
  test("Test Thrift "){
    val userInfo= service.getUserInfoById("0470142e-274c-11ed-aa17-0242ac120002")
      println(s"UserInfo:${userInfo}")
  }
  test("Test Thrift when set id empty ") {
    val userInfo = service.getUserInfoById("")
    println(s"UserInfo:${userInfo}")
  }

}
