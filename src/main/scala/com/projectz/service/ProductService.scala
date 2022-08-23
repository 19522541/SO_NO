package com.projectz.service

import com.projectz.domain.{ProductDetail, ProductID, UserID, UserInfo}
import com.projectz.repository.CacheRepository
import com.twitter.util.Future

import javax.inject.Inject

trait ProductService {
   def getProduct(id:String):Future[ProductDetail]
  def setProduct(id: ProductID, info: ProductDetail): Unit
  def test(id: ProductID): Unit
}

case class ProductServiceImpl @Inject()() extends ProductService {
  override def setProduct(id: ProductID, info: ProductDetail): Unit = {
    println("Set Product")
  }

  override def getProduct(id: String): Future[ProductDetail] = ???
  override def test(id: ProductID): Unit = {
    println(s"product ID ${id.id}")
  }
}
