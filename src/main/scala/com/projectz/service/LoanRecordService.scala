package com.projectz.service

import com.projectz.domain.request.{GetLoanRecordRequest, PostLoanRecordRequest, PutLoanRecordRequest}
import com.projectz.domain.{LoanRecord, RecordModifier, User}
import com.projectz.repository.LoanRecordRepository
import com.twitter.util.Future

import java.util.Date
import javax.inject.Inject
import scala.concurrent.duration.Deadline

trait LoanRecordService{
  def getRecord(getLoanRecordRequest: GetLoanRecordRequest):Set[LoanRecord]
  def addRecord(postLoanRecordRequest: PostLoanRecordRequest) :LoanRecord
  def deleteRecord(id:String):LoanRecord
  def updateRecord(putLoanRecordRequest: PutLoanRecordRequest):LoanRecord
}
 class LoanRecordServiceImpl @Inject()(
                LoanRecordRepository:LoanRecordRepository[User,LoanRecord]
                                     ) extends LoanRecordService {

  override def deleteRecord(id: String): LoanRecord = ???

   override def addRecord(postLoanRecordRequest: PostLoanRecordRequest): LoanRecord = ???

   override def updateRecord(putLoanRecordRequest: PutLoanRecordRequest): LoanRecord = ???

   override def getRecord(getLoanRecordRequest: GetLoanRecordRequest):Set[LoanRecord] = ???
 }

