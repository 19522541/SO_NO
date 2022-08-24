package com.projectz.domain

import java.util.Date

case class LoanRecord(id :String,loanAmount:Long, borrowerId:String,lenderId:String,loanReason:String="",createdDate:Date)

