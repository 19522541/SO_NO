package com.projectz.service

import com.twitter.util.Future
import di.labs.domain.thrift.TUserDetail
import di.labs.service.TUser

import javax.inject.Inject

trait UserService {
  def getUserById(id:String):Future[TUserDetail]
  def getUserByName(name:String):Future[Seq[TUserDetail]]
}
class  UserServiceImpl @Inject()(userThrift: TUser.MethodPerEndpoint) extends UserService {
  override def getUserById(id: String): Future[TUserDetail] = {
    userThrift.getUserById(id)
  }

  override def getUserByName(name: String): Future[Seq[TUserDetail]]  ={
    userThrift.getUserByName(name)
  }


 }
