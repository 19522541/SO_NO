package com.projectz.domain.response

import java.util.Date


case class LoanResponse(loanAmount:Long, borrower:String,lender:String,loanReason:String="",createDate:Date=new Date())
