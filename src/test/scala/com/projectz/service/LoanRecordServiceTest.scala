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
      borrowerId = "USERID002",
      lenderId = "USERID003",
      loanReason = "MUON MUA NHA"   )
   val createdResult= service.addRecord(addRequest)
    assert(createdResult.lenderId==addRequest.lenderId)
  }
  test( "Get borrowers of lender"){
    val borrowers= service.getBorrowersOfLender("001","VUONG")
    assert(borrowers.size==2)
  }
  test( "Loan Record of Borrower" ){
    val loadRecord = service.getLoanDetailOfBorrower(lenderId = "001",borrowerId = "002")
    assert(loadRecord.size==1  )
  }
}
