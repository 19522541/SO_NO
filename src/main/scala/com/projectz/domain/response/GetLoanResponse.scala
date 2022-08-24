package com.projectz.domain.response

import com.projectz.domain.LoanRecord

case class GetLoanResponse (UserID:String, gottenLoanRecords:Seq[LoanRecord]){

}
