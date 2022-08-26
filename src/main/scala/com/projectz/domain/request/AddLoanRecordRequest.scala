package com.projectz.domain.request

import com.projectz.domain.LoanRecord
import com.twitter.finatra.http.annotations.QueryParam
import com.twitter.finatra.validation.constraints.NotEmpty
import java.util.Date

case class AddLoanRecordRequest(@QueryParam lender: String, borrower: String, loanReason: String, loanAmount: Long)

