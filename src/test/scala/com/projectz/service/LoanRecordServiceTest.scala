package com.projectz.service

import com.google.inject.Guice
import com.projectz.domain.LoanRecord
import com.projectz.domain.request.AddLoanRecordRequest
import com.projectz.module.TestModule
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
    val borrowers= service.getBorrowersOfLender("LUONG HUU VUong","ng Hữu Vư")
    assert(borrowers.size==1)
  }
  test( "Get borrowers of lender empty keyword"){
    val borrowers= service.getBorrowersOfLender("Nguyễn Văn B","")
    assert(borrowers.size==1)
  }
  test( "Get borrowers of anonymous lender"){
    val borrowers= service.getBorrowersOfLender("","")
    assert(borrowers.size==0)
  }
  test( "Loan Record of Borrower" ){
    val loadRecord = service.getLoanDetailOfBorrower(lender = "LUONG HUU VUong",borrower = "Lương Hữu Vương")
    assert(loadRecord.size==1 )
  }
  test("is DB connected??"){
   assert(service.isConnect())
  }

}
