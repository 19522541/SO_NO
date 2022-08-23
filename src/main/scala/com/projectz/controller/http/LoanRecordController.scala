package com.projectz.controller.http

import com.projectz.domain.{LoanRecord, RecordModifier, User}
import com.projectz.service.LoanRecordService
import com.twitter.finatra.http.Controller

import javax.inject.Inject

class LoanRecordController @Inject()(loanRecordService: LoanRecordService) extends Controller{
  get("/loan"){ request:User=>{
    loanRecordService.getRecord(request)
    response.ok()
    }
  }
  patch("/loan"){request:String=>{
    loanRecordService.deleteRecord(request)
    response.ok()
  }}
  post("/loan") {
    request:LoanRecord=>{
     loanRecordService.addRecord(request)
      response.ok()
    }
  }
  delete("/loan"){
    request:String=>{
  loanRecordService.deleteRecord(request)
      response.ok()
    }

  }

}
