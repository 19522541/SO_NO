package com.projectz.repository

import com.projectz.domain.{LoanRecord, User}
trait  LoanRecordRepository[X,Y]{
    def add(x:X,y:Y):Unit
    def show(x:X):Set[Y]
}
class LoadRecordStore extends LoanRecordRepository[User,LoanRecord] {
    var record: Set[LoanRecord] =  Set[LoanRecord]()


  override def add(x: User, y: LoanRecord):Unit = {
      record+(y)
        }

  override def show(x: User): Set[LoanRecord] = record.filter(_.lender.id==x.id)
}
