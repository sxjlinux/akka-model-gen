/*****************************************************
 ** This file is 100% ***GENERATED***, DO NOT EDIT! **
 *****************************************************/
package com.apuex.sales.json;

import com.wincom.dcim.message._
import play.api.libs.json._

trait JsonFormat {
  implicit val productVoFormat = Json.format[ProductVo]

  implicit val createProductCmdFormat = Json.format[CreateProductCmd]
  implicit val createProductEvtFormat = Json.format[CreateProductEvt]
  implicit val retrieveProductCmdFormat = Json.format[RetrieveProductCmd]

  implicit val changeProductNameCmdFormat = Json.format[ChangeProductNameCmd]
  implicit val changeProductNameEvtFormat = Json.format[ChangeProductNameEvt]

  implicit val changeProductUnitCmdFormat = Json.format[ChangeProductUnitCmd]
  implicit val changeProductUnitEvtFormat = Json.format[ChangeProductUnitEvt]

  implicit val changeUnitPriceCmdFormat = Json.format[ChangeUnitPriceCmd]
  implicit val changeUnitPriceEvtFormat = Json.format[ChangeUnitPriceEvt]

  implicit val updateProductSalesCmdFormat = Json.format[UpdateProductSalesCmd]
  implicit val ProductSalesVoFormat = Json.format[ProductSalesVo]
  implicit val getProductSalesCmdFormat = Json.format[GetProductSalesCmd]

  implicit val orderVoFormat = Json.format[OrderVo]

  implicit val createOrderCmdFormat = Json.format[CreateOrderCmd]
  implicit val createOrderEvtFormat = Json.format[CreateOrderEvt]
  implicit val retrieveOrderCmdFormat = Json.format[RetrieveOrderCmd]

  implicit val addOrderItemsCmdFormat = Json.format[AddOrderItemsCmd]
  implicit val addOrderItemsEvtFormat = Json.format[AddOrderItemsEvt]
  implicit val removeOrderItemsCmdFormat = Json.format[RemoveOrderItemsCmd]
  implicit val removeOrderItemsEvtFormat = Json.format[RemoveOrderItemsEvt]

  implicit val orderItemVoFormat = Json.format[OrderItemVo]

}

