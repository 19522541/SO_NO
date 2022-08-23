/**
 * Generated by Scrooge
 *   version: 20.4.0
 *   rev: 2d3edcfb2b025024319594ccdd4d7afe488fa6b4
 *   built at: 20200402-030121
 */
package com.projectz.service

import com.twitter.finagle.{
  Filter => _,
  thrift => _,
  _
}
import com.twitter.finagle.stats.{NullStatsReceiver, StatsReceiver}
import com.twitter.finagle.thrift.RichServerParam
import com.twitter.io.Buf
import com.twitter.util.Future
import org.apache.thrift.protocol._
import org.apache.thrift.TApplicationException
import org.apache.thrift.transport.TMemoryInputTransport


@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
class TUserCacheService$FinagleService(
  iface: TUserCacheService[Future],
  serverParam: RichServerParam
) extends com.twitter.finagle.Service[Array[Byte], Array[Byte]] {
  import TUserCacheService._

  @deprecated("Use com.twitter.finagle.thrift.RichServerParam", "2017-08-16")
  def this(
    iface: TUserCacheService[Future],
    protocolFactory: TProtocolFactory,
    stats: StatsReceiver = NullStatsReceiver,
    maxThriftBufferSize: Int = Thrift.param.maxThriftBufferSize,
    serviceName: String = "TUserCacheService"
  ) = this(iface, RichServerParam(protocolFactory, serviceName, maxThriftBufferSize, stats))

  @deprecated("Use com.twitter.finagle.thrift.RichServerParam", "2017-08-16")
  def this(
    iface: TUserCacheService[Future],
    protocolFactory: TProtocolFactory,
    stats: StatsReceiver,
    maxThriftBufferSize: Int
  ) = this(iface, protocolFactory, stats, maxThriftBufferSize, "TUserCacheService")

  @deprecated("Use com.twitter.finagle.thrift.RichServerParam", "2017-08-16")
  def this(
    iface: TUserCacheService[Future],
    protocolFactory: TProtocolFactory
  ) = this(iface, protocolFactory, NullStatsReceiver, Thrift.param.maxThriftBufferSize)

  def serviceName: String = serverParam.serviceName
  private[this] val filters = new Filter(serverParam)

  private[this] def protocolFactory: TProtocolFactory = serverParam.restrictedProtocolFactory

  protected val serviceMap: _root_.scala.collection.mutable.HashMap[String, _root_.com.twitter.finagle.Service[(TProtocol, Int), Array[Byte]]] =
    new _root_.scala.collection.mutable.HashMap[String, _root_.com.twitter.finagle.Service[(TProtocol, Int), Array[Byte]]]()

  protected def addService(name: String, service: _root_.com.twitter.finagle.Service[(TProtocol, Int), Array[Byte]]): Unit = {
    serviceMap(name) = service
  }

  final def apply(request: Array[Byte]): Future[Array[Byte]] = {
    val inputTransport = new TMemoryInputTransport(request)
    val iprot = protocolFactory.getProtocol(inputTransport)

    try {
      val msg = iprot.readMessageBegin()
      val service = serviceMap.get(msg.name)
      service match {
        case _root_.scala.Some(svc) =>
          svc((iprot, msg.seqid))
        case _ =>
          TProtocolUtil.skip(iprot, TType.STRUCT)
          Future.value(Buf.ByteArray.Owned.extract(
            filters.exception(msg.name, msg.seqid, TApplicationException.UNKNOWN_METHOD,
              "Invalid method name: '" + msg.name + "'")))
      }
    } catch {
      case e: Exception => Future.exception(e)
    }
  }
  // ---- end boilerplate.

  addService("ping", {
    val methodService = new _root_.com.twitter.finagle.Service[Ping.Args, Ping.SuccessType] {
      def apply(args: Ping.Args): Future[Ping.SuccessType] = {
        if (_root_.com.twitter.finagle.tracing.Trace.isActivelyTracing) {
          _root_.com.twitter.finagle.tracing.Trace.recordRpc("ping")
        }
        iface.ping()
      }
    }
  
    filters.ping.andThen(methodService)
  })
  addService("addUser", {
    val methodService = new _root_.com.twitter.finagle.Service[AddUser.Args, AddUser.SuccessType] {
      def apply(args: AddUser.Args): Future[AddUser.SuccessType] = {
        if (_root_.com.twitter.finagle.tracing.Trace.isActivelyTracing) {
          _root_.com.twitter.finagle.tracing.Trace.recordRpc("addUser")
        }
        iface.addUser(args.userInfo)
      }
    }
  
    filters.addUser.andThen(methodService)
  })
  addService("getUser", {
    val methodService = new _root_.com.twitter.finagle.Service[GetUser.Args, GetUser.SuccessType] {
      def apply(args: GetUser.Args): Future[GetUser.SuccessType] = {
        if (_root_.com.twitter.finagle.tracing.Trace.isActivelyTracing) {
          _root_.com.twitter.finagle.tracing.Trace.recordRpc("getUser")
        }
        iface.getUser(args.userId)
      }
    }
  
    filters.getUser.andThen(methodService)
  })
}