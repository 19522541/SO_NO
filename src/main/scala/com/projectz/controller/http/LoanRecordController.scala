package com.projectz.controller.http

import com.projectz.domain.LoanRecord
import com.projectz.domain.request.{AddLoanRecordRequest, GetBorrowerRequest, GetLoanRecordRequest}
import com.projectz.service.LoanService
import com.twitter.finatra.http.Controller

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class LoanRecordController @Inject()(loanService: LoanService) extends Controller {
  val formatter = new SimpleDateFormat("dd-MMM-yyyy")

  get("/loan/detail") { request: GetBorrowerRequest => {
    val loanRecordsOfBorrower= loanService.getLoanDetailOfBorrower(request.lenderId,request.borrowerId)
   loanRecordsOfBorrower
  }
  }

  get("/loan") { request: GetLoanRecordRequest => {
    val borrowers= loanService.getBorrowersOfLender(request.lenderId,request.keyword)
    borrowers
  }
  }


  post("/loan/:lender_id") {
    request: AddLoanRecordRequest => {
      val loanRecord = LoanRecord(id = "USER001",
        loanAmount = request.loanAmount,
        borrowerId = request.borrowerId,
        lenderId = request.lenderId,
        loanReason = request.loanReason,
        createdDate = new Date())
      val addedLoanRecord = loanService.addRecord(loanRecord)
      addedLoanRecord
    }
  }


}
