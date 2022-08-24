package com.projectz.domain.request

import com.twitter.finatra.http.annotations.QueryParam

case class UpdateLoanRecordRequest (@QueryParam id:String,loanAmount:Long=0, deadline:String="", isPaired:Boolean)
