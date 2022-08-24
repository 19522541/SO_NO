package com.projectz.service

import com.google.inject.Guice
import com.projectz.domain.LoanRecord
import com.projectz.module.TestModule
import com.twitter.inject.{Injector, IntegrationTest}

import java.util.Date

class LoanRecordServiceTest extends IntegrationTest{
  override protected def injector: Injector = Injector(Guice.createInjector(Seq(TestModule):_*))
  private val service = injector.instance[LoanService]
  test( "CREATE new LoanRecord"){
    val loanRecord:LoanRecord=LoanRecord(
      id="001",
      loanAmount = 10000,
      borrowerId = "USERID002",
      lenderId = "USERID003",
      loanReason = "MUON MUA NHA",
      createdDate = new Date())
   val createdResult= service.addRecord(loanRecord)
    assert(createdResult.id==loanRecord.id)
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
