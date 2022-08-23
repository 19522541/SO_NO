package com.twitter.finatra.module

import com.google.common.net.MediaType._
import com.google.inject.Inject
import com.twitter.finagle.http.Status
import com.twitter.finatra.exception.ApiError
import com.twitter.finatra.http.marshalling
import com.twitter.finatra.http.marshalling.{DefaultMessageBodyWriter, WriterResponse}
import com.twitter.finatra.jackson.ScalaObjectMapper
import com.twitter.inject.Logging

class CustomResponseWriter @Inject()(mapper: ScalaObjectMapper)
  extends DefaultMessageBodyWriter
    with Logging {
  override def write(obj: Any): WriterResponse = {
    obj match {
      case ex: Throwable =>
        marshalling.WriterResponse(
          contentType = JSON_UTF_8.toString,
          body = mapper.writeValueAsString(
            ApiError(
              Status.InternalServerError.code,
              "internal_error",
              ex.getMessage
            )
          )
        )
      case v: String =>
        marshalling.WriterResponse(contentType = JSON_UTF_8.toString, body = v)
      case v: Any =>
        marshalling.WriterResponse(
          contentType = JSON_UTF_8.toString,
          body = mapper.writeValueAsString(v)
        )
    }
  }
}
