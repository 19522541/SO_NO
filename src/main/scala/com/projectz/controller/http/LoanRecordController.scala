package com.projectz.controller.http

import com.projectz.domain.LoanRecord
import com.projectz.domain.request.{AddLoanRecordRequest, GetBorrowerRequest, GetLoanRecordRequest}
import com.projectz.service.LoanService
import com.twitter.finatra.http.Controller

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class LoanRecordController @Inject()(loanService: LoanService) extends Controller {

  get("/loan/detail") { request: GetBorrowerRequest => {
    val loanRecordsOfBorrower= loanService.getLoanDetailOfBorrower(request.lender,request.borrower)
    loanRecordsOfBorrower
  }
  }

  get("/loan") { request: GetLoanRecordRequest => {
    try {
      val borrowers= loanService.getBorrowersOfLender(request.lender,request.keyword)
      println(s"LoanRecordCotroller::get::keyword== ${request.keyword}")
      println(s"LoanRecordCotroller::get::keyword== ${request.lender}")

      borrowers
    } catch {
      case ex: Throwable => logger.error(s"LoanController::getLoan ${ex.getMessage}")
    }
  }
  }


  post("/loan/:lender") {
    request: AddLoanRecordRequest => {
      val addedLoanRecord = loanService.addRecord(request)
      addedLoanRecord
    }
  }


}
