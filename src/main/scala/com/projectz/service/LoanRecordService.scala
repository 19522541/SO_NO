package com.projectz.service

import com.projectz.domain.{LoanRecord, RecordModifier, User}
import com.projectz.repository.LoanRecordRepository
import com.twitter.util.Future

import java.util.Date
import javax.inject.Inject
import scala.concurrent.duration.Deadline

trait LoanRecordService{
  def getRecord(user:User):Future[Set[LoanRecord]]
  def addRecord(loanRecord:LoanRecord) :LoanRecord
  def deleteRecord(id:String):LoanRecord
  def updateRecord(recordModifier: RecordModifier):LoanRecord
}
class LoanRecordServiceImpl @Inject()(
                LoanRecordRepository:LoanRecordRepository[User,LoanRecord]
                                     ) extends LoanRecordService {

  def initID():String{

  }

  override def getRecord(user: User): Future[Set[LoanRecord]] =Future{
    LoanRecordRepository.show(user)
  }

  override def addRecord(user: User, loanRecord: LoanRecord): LoanRecord = {
    LoanRecordRepository.add(user,loanRecord)
    loanRecord
  }

  override def deleteRecord(id: String): LoanRecord = ???

  override def updateRecord(recordModifier: RecordModifier): LoanRecord = ???
}

