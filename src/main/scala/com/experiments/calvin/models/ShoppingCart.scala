package com.experiments.calvin.models

case class ShoppingCart(items: List[Item])

case class Item(id: Int, name: String)
//case class ItemV2(id: Int, name: String, description: String)
//case class ItemV3(id: Int, fullName: String, description: String)
