package com.projectz.domain

import java.util.Date

case class LoanRecord(id:String,loadAmount:Long, deadline:Date, borrower:User,borrowDate:Date,lender:User)
object LoanRecord{
  def mixLoanRecords(rawRecord:LoanRecord,modifierInfo:LoanRecord): LoanRecord ={
    var lender= if (modifierInfo.lender.toString !="") modifierInfo.lender else rawRecord.lender
    var loadAmount= if (modifierInfo.loadAmount.toString !="") modifierInfo.loadAmount else rawRecord.loadAmount
    var deadline= if (modifierInfo.deadline.toString !="") modifierInfo.deadline else rawRecord.deadline
    var borrowDate= if (modifierInfo.borrowDate.toString !="") modifierInfo.borrowDate else rawRecord.borrowDate
    var borrower= if (modifierInfo.borrower.toString !="") modifierInfo.borrower else rawRecord.borrower

    var recordItem=LoanRecord(rawRecord.id,loadAmount,deadline,borrower,borrowDate,lender)
    recordItem
  }
}
