package com.experiments.calvin.models

import play.api.libs.json.{Format, Json}

case class Item(id: Int, name: String, description: String, nogeen: String)

case object Item {
  implicit val format: Format[Item] = Json.format
}

case class ShoppingCart(items: Item)

case object ShoppingCart {
  implicit val format: Format[ShoppingCart] = Json.format
}

//case class ItemV2(id: Int, name: String, description: String)
//case class ItemV3(id: Int, fullName: String, description: String)
