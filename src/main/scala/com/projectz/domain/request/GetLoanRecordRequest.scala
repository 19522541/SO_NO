package com.projectz.domain.request

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}
import com.twitter.finatra.validation.constraints.NotEmpty

case class GetLoanRecordRequest(@QueryParam @NotEmpty id: String)
