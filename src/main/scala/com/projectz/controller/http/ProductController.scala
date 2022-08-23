package com.projectz.controller.http

import com.projectz.domain.request.{GetProductRequest, PostProductRequest}
import com.projectz.service.ProductService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import javax.inject.Inject

class ProductController @Inject() (productService: ProductService ) extends Controller {
//  get("/getProduct") { request: GetProductRequest => {
//    ProductService.getProduct(request.productID)
//    response.ok()
//  }
//  }
  get("/getProduct"){ request:PostProductRequest=> response.ok("lUONG Huu vuONG")}
  post("/createProduct") { request: PostProductRequest =>
    //productService.test(request.productID)
    println(request)
    productService.test(request.productID)
    response.ok("luong  HUu Vuong")

  //ProductService.test(request.productID)
   // ProductService.setProduct(request.productID,request.productDetail)
  }
}
