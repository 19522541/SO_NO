package com.projectz.domain.request

import com.projectz.domain.LoanRecord
import com.twitter.finatra.http.annotations.QueryParam
import com.twitter.finatra.validation.constraints.NotEmpty

case class AddLoanRecordRequest(@QueryParam @NotEmpty id: String ,loanRecord:LoanRecord)
