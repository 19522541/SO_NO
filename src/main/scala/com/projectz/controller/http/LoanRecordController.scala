package com.projectz.controller.http

import com.projectz.domain.LoanRecord
import com.projectz.domain.request.{AddLoanRecordRequest, GetBorrowerRequest, GetLoanRecordRequest}
import com.projectz.service.{LoanService, UserService}
import com.twitter.finatra.http.Controller

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class LoanRecordController @Inject()(loanService: LoanService,userService: UserService) extends Controller {

  get("/loan/detail") { request: GetBorrowerRequest => {
    val loanRecordsOfBorrower= loanService.getLoanDetails(request.lender,request.borrower)
    loanRecordsOfBorrower
  }
  }

  get("/loan") { request: GetLoanRecordRequest => {
      val borrowers= loanService.getBorrowers(request.lender,request.keyword)
      borrowers
  }
  }


  post("/loan/:lender") {
    request: AddLoanRecordRequest => {
      val addedLoanRecord = loanService.addRecord(request)
      addedLoanRecord
    }
  }


}
