package com.projectz.domain.request

import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}

case class GetBorrowerRequest (@RouteParam lenderId:String, @RouteParam borrowerId:String="" ){

}
