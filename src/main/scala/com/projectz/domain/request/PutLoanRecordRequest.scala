package com.projectz.domain.request

import com.projectz.domain.RecordModifier
import com.twitter.finatra.http.annotations.QueryParam

case class PutLoanRecordRequest (@QueryParam id:String, modifyDetail:RecordModifier)
