package com.experiments.calvin.registry

import com.experiments.calvin.models.{Item, ShoppingCart}
import com.lightbend.lagom.scaladsl.playjson.{JsonMigration, JsonMigrations, JsonSerializer, JsonSerializerRegistry}

import scala.collection.immutable

object ShoppingCartSerializerRegistry extends JsonSerializerRegistry {

  import play.api.libs.json._

  override val serializers = Vector(
    JsonSerializer(Json.format[ShoppingCart])
  )

//  private val itemAddedMigration = new JsonMigration(2) {
//    override def transform(fromVersion: Int, json: JsObject): JsObject = {
//      if (fromVersion < 2) {
//        json.underlying.get("items") +("discount" -> JsNumber(0.0D))
//      } else {
//        json
//      }
//    }
//  }
//
//  override def migrations = Map[String, JsonMigration](
//    classOf[Item].getName -> itemAddedMigration
//  )

  private val addDefaultDescription = JsPath.json.update((JsPath \ "items" \ "description").json.put(JsString("No description.")))
  private val addDefaultNogEen = JsPath.json.update((JsPath \ "items" \ "nogeen").json.put(JsString("No gain")))

  override def migrations: Map[String, JsonMigration] = Map[String, JsonMigration](
    JsonMigrations.transform[ShoppingCart](immutable.SortedMap(
      1 -> addDefaultDescription,
      2 -> addDefaultNogEen
    ))
  )
}