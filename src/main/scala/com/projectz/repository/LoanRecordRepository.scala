package com.projectz.repository

import com.projectz.domain.{LoanRecord, RecordModifier}

import java.lang.String

trait  LoanRecordRepository[X,Y,Z]{
    def add(y:Y):Option[Y]
    def show(x:X):Seq[Y]
    def delete(x:X):Y
    def update(x:X,z:Z):Y
}
class LoadRecordStore extends LoanRecordRepository[String,LoanRecord,RecordModifier] {
    var record: Seq[LoanRecord] =  Seq[LoanRecord]()


  override def show(x: String): Seq[LoanRecord] = record.filter(_.lender.id==x)

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

  override def update(x: String, z: RecordModifier): LoanRecord = ???
}
