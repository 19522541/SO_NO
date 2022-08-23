package com.projectz.domain.request

import com.projectz.domain.{ProductDetail, ProductID}

case class PostProductRequest(productID: ProductID,productDetail: ProductDetail ){}
