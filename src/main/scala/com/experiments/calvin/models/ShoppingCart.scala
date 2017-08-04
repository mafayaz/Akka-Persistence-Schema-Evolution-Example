package com.experiments.calvin.models

case class ShoppingCartV1(items: List[ItemV1])
case class ShoppingCartV2(items: List[ItemV2])
case class ShoppingCartV3(items: List[ItemV3])

case class ItemV1(id: Int, name: String)
case class ItemV2(id: Int, name: String, description: String)
case class ItemV3(id: Int, fullName: String, description: String)
