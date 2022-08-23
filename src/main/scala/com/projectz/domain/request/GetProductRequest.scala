package com.projectz.domain.request

import com.twitter.finatra.http.annotations.QueryParam

case class GetProductRequest(@QueryParam productID:String){}
