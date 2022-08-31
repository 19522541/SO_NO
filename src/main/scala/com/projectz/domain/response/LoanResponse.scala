package com.projectz.domain.response

import di.labs.domain.thrift.TUserDetail
import di.labs.service.TUser

import java.util.Date


case class LoanResponse(loanAmount:Long, borrower:UserInfo,lender:UserInfo, loanReason:String="", createdDate:Date=new Date())
