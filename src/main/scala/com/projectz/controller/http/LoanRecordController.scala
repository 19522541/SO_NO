package com.projectz.controller.http

import com.projectz.domain.request.{AddLoanRecordRequest, GetLoanRecordRequest, UpdateLoanRecordRequest}
import com.projectz.domain.{LoanRecord, UserID}
import com.projectz.domain.response.LoanResponse
import com.projectz.service.LoanService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import java.util.Date
import javax.inject.Inject

class LoanRecordController @Inject()(loanRecordService: LoanService) extends Controller {
  get("/loan/:id") { request: GetLoanRecordRequest => {
    //val userLoanRecord:Seq[LoanRecord]  =loanRecordService.getRecord(request)
    LoanResponse(id = "002",
      LoanRecord(id = "002",
        loanAmount = 10000,
        deadline = "Sun 2022.08.18",
        borrower = "USERCODE5",
        borrowDate = "Sun 2022.07.18",
        lender = "USERCODE1")
    )

  }
  }
  put("/loan:id") { request: UpdateLoanRecordRequest => {
    //val updatedRecordItem: LoanRecord = loanRecordService.updateRecord(request)

    LoanResponse(
      id = "001",
      LoanRecord(id = "001",
        loanAmount = 10000,
        deadline = "Sun 2022.08.18",
        borrower = "USERCODE5",
        borrowDate = "Sun 2022.07.18",
        lender = "USERCODE1")
    )
  }
  }
  post("/loan/:id") {
    request: AddLoanRecordRequest => {
      //val addedItemRecordItem :LoanRecord=  loanRecordService.addRecord(request.loanRecord)
      LoanResponse(id = "003",
        LoanRecord(id = "003",
          loanAmount = 10000,
          deadline = "Sun 2022.08.18",
          borrower = "USERCODE2",
          borrowDate = "Sun 2022.07.18",
          lender = "USERCODE1")
      )
    }
  }
  delete("/loan/:id") {
    request: Request => {
      //val deletedRecordItem: LoanRecord=   loanRecordService.deleteRecord(request.getParam("id"))
      LoanResponse(id = "003",
        LoanRecord(id = "003",
          loanAmount = 10000,
          deadline = "Sun 2022.08.18",
          borrower = "USERCODE2",
          borrowDate = "Sun 2022.07.18",
          lender = "USERCODE1"))
    }
  }

}
