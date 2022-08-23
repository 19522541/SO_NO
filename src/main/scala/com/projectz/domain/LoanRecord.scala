package com.projectz.domain

import java.util.Date

case class LoanRecord(id:String,loadAmount:Long, deadline:Date, borrower:User,borrowDate:Date,lender:User)
