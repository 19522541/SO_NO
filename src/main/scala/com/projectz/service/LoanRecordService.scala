package com.projectz.service

import com.projectz.domain.request.{GetLoanRecordRequest, PostLoanRecordRequest, PutLoanRecordRequest}
import com.projectz.domain.{LoanRecord, RecordModifier, User}
import com.projectz.repository.LoanRecordRepository
import com.twitter.util.Future

import java.util.Date
import javax.inject.Inject
import scala.concurrent.duration.Deadline

trait LoanRecordService {
  def getRecord(getLoanRecordRequest: GetLoanRecordRequest): Seq[LoanRecord]

  def addRecord(id: String, loanRecord: LoanRecord): Option[LoanRecord]

  def deleteRecord(id: String): Seq[LoanRecord]

  def updateRecord(putLoanRecordRequest: PutLoanRecordRequest): LoanRecord
}

class LoanRecordServiceImpl @Inject()(
                                       LoanRecordRepository: LoanRecordRepository[String, LoanRecord,RecordModifier]
                                     ) extends LoanRecordService {

  override def deleteRecord(id: String): Seq[LoanRecord] = {
    LoanRecordRepository.delete(id)
  }

  override def updateRecord(putLoanRecordRequest: PutLoanRecordRequest): LoanRecord = ???

  override def getRecord(getLoanRecordRequest: GetLoanRecordRequest): Seq[LoanRecord] = {
    LoanRecordRepository.show(getLoanRecordRequest.id)

  }

  override def addRecord(id: String, loanRecord: LoanRecord): Option[LoanRecord] = {
    if (id == loanRecord.lender.id) {
      LoanRecordRepository.add(loanRecord)
    }
    None
  }
}

