package com.experiments.calvin.registry

import com.experiments.calvin.models.{Item, ShoppingCart}
import com.lightbend.lagom.scaladsl.playjson.{JsonMigration, JsonSerializer, JsonSerializerRegistry}

object ShoppingCartSerializerRegistry extends JsonSerializerRegistry {

  import play.api.libs.json._

  override val serializers = Vector(
    JsonSerializer(Json.format[ShoppingCart])
  )

//  private val shoppingCartV3Migration = new JsonMigration(2) {
//    override def transform(fromVersion: Int, json: JsObject): JsObject = {
//      if (fromVersion < 2) {
//        json + ("discount" -> JsNumber(0.0D))
//      } else {
//        json
//      }
//    }
//  }

//  override def migrations = Map[String, JsonMigration](
//    classOf[ShoppingCart].getName -> shoppingCartV3Migration
//  )
}