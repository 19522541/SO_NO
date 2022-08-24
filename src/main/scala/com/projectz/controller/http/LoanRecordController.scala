package com.projectz.controller.http

import com.projectz.domain.request.{AddLoanRecordRequest, GetBorrowerRequest, GetLoanRecordRequest, UpdateLoanRecordRequest}
import com.projectz.domain.{LoanRecord, UserID}
import com.projectz.domain.response.LoanResponse
import com.projectz.service.LoanService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class LoanRecordController @Inject()() extends Controller {
  val formatter = new SimpleDateFormat("dd-MMM-yyyy")
  get("/loan/detail") { request: GetBorrowerRequest => {

    Seq(
      LoanResponse(
        loanAmount = 150000,
        borrower = "Hao",
        lender = "Thien Vi",
        loanReason = "Muon xay nha",
        createdDate = formatter.parse("31-Dec-2022")
      )
    )
  }
  }

  get("/loan") { request: GetLoanRecordRequest => {
    println(request.lenderId)
    println(request.keyword)
    //val userLoanRecord:Seq[LoanRecord]  =loanRecordService.getRecord(request)
    Seq( LoanResponse(
      loanAmount = 150000,
      borrower = "Hao",
      lender = "Thien Vi",
      loanReason = "Muon xay nha",
      createdDate = formatter.parse("31-Dec-2022")
    ),
      LoanResponse(
        loanAmount = 120000,
        borrower = "Vuong",
        lender = "Thien Vi",
        loanReason = "Muon xay nha",
        createdDate = formatter.parse("31-Dec-2022")
      )
    )
  }
  }


  post("/loan/:lender_id") {
    request: AddLoanRecordRequest => {
      println(s"Add request ${request.lenderId}=====")
      //val addedItemRecordItem :LoanRecord=  loanRecordService.addRecord(request.loanRecord)
      LoanRecord(
        id="001",
        loanAmount = 100000,
        borrowerId = "USERCODE2",
        lenderId = "USERCODE1",
        loanReason = "het tien do xang",
        createdDate = formatter.parse("31-Dec-2022")
      )
    }
  }

  //  delete("/loan/:id") {
  //    request: Request => {
  //      //val deletedRecordItem: LoanRecord=   loanRecordService.deleteRecord(request.getParam("id"))
  //      LoanResponse(
  //        loanAmount = 10000,
  //        borrower = "USERCODE2",
  //        lender = "USERCODE1",
  //      )
  //    }
  //  }
  //  put("/loan:id") { request: UpdateLoanRecordRequest => {
  //    //val updatedRecordItem: LoanRecord = loanRecordService.updateRecord(request)
  //    LoanResponse(
  //        loanAmount = 10000,
  //        borrower = "USERCODE5",
  //        lender = "USERCODE1",
  //
  //    )
  //  }
  //  }
}
