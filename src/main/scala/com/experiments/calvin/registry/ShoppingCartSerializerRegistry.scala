//package com.experiments.calvin.registry
//
//import com.experiments.calvin.models.ShoppingCartV3
//import com.lightbend.lagom.scaladsl.playjson.{JsonMigration, JsonSerializer, JsonSerializerRegistry}
//
//class ShoppingCartSerializerRegistry extends JsonSerializerRegistry {
//
//  import play.api.libs.json._
//
//  override val serializers = Seq(
//    JsonSerializer(Json.format[MigratedEvent])
//  )
//
//  private val shoppingCartV3Migration = new JsonMigration(2) {
//    override def transform(fromVersion: Int, json: JsObject): JsObject = {
//      if (fromVersion < 2) {
//        json + ("discount" -> JsNumber(0.0D))
//      } else {
//        json
//      }
//    }
//  }
//
//  override def migrations = Map[String, JsonMigration](
//    classOf[ShoppingCartV3].getName -> shoppingCartV3Migration
//  )
//}