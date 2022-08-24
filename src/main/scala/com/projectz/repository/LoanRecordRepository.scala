package com.projectz.repository

import com.projectz.domain.LoanRecord
import com.projectz.domain.request.UpdateLoanRecordRequest

import java.lang.String

trait  LoanRecordRepository[X,Y]{
    def add(y:Y):Option[Y]
    def show(x:X):Seq[Y]
    def delete(x:X):Y

}
class LoadRecordStore extends LoanRecordRepository[String,LoanRecord] {
    var record: Seq[LoanRecord] =  Seq[LoanRecord]()


  override def show(x: String): Seq[LoanRecord] = ???

  override def add(y: LoanRecord): Option[LoanRecord] = {
      try {
        record:+y
        Some(y)
      }catch {
        case e:Exception=> None
      }
  }


  override def delete(x: String): LoanRecord = {
    record.dropWhile(_.id==x).head
  }

   def update( updateLoanRecordRequest: UpdateLoanRecordRequest): LoanRecord = ???
}
