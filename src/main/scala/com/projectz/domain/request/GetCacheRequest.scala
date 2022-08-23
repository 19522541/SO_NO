package com.projectz.domain.request

import com.twitter.finatra.http.annotations.QueryParam

/**
 * Created by SangDang on 9/16/16.
 */
case class GetCacheRequest(@QueryParam userID: String) {}
