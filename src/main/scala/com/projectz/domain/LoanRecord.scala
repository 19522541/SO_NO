package com.projectz.domain

import java.util.Date

case class LoanRecord(id :String,loanAmount:Long, borrower:String,lender:String,loanReason:String="",createDate:Date)

