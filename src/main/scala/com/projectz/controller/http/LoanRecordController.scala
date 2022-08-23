package com.projectz.controller.http

import com.projectz.domain.request.{GetLoanRecordRequest, PostLoanRecordRequest, PutLoanRecordRequest}
import com.projectz.domain.{LoanRecord, RecordModifier, User}
import com.projectz.service.LoanRecordService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import javax.inject.Inject

class LoanRecordController @Inject()(loanRecordService: LoanRecordService) extends Controller {
  get("/loan/:id") { request: GetLoanRecordRequest=> {
   val userLoanRecord:Seq[LoanRecord]  =loanRecordService.getRecord(request)
    response.ok(userLoanRecord.toString())
  }
  }
  put("/loan:id") { request: PutLoanRecordRequest => {
    val updatedRecordItem: LoanRecord = loanRecordService.updateRecord(request)
    response.ok(updatedRecordItem)
  }
  }
  post("/loan/:id") {
    request: PostLoanRecordRequest => {
    val addedItemRecordItem :Option[LoanRecord]=  loanRecordService.addRecord(request.id,request.loanRecord)
      response.ok( addedItemRecordItem.get.toString)
    }
  }
  delete("/loan/:id") {
    request: Request => {
   val deletedRecordItem: LoanRecord=   loanRecordService.deleteRecord(request.getParam("id"))
      response.ok(deletedRecordItem.toString)
    }
  }

}
