package com.projectz.domain.request

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}
import com.twitter.finatra.validation.constraints.NotEmpty


case class GetLoanRecordRequest(@RouteParam  lender: String,
                                @RouteParam keyword: String = "")
// camelCase
// sake_case
// PascalCase
