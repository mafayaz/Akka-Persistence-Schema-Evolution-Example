package com.experiments.calvin.serialization

import java.nio.charset.Charset

import akka.serialization.SerializerWithStringManifest
import com.experiments.calvin.models._
import play.api.libs.json.{Json, Reads}

/**
  * Class is responsible for serializing ShoppingCarts before they make their way into the Journal/Snapshot journal
  * and when retrieving these items from the journals.
  */
class ShoppingCartSerializer extends SerializerWithStringManifest {
  val ShoppingCartV1Manifest = classOf[ShoppingCartV1].getName
  val ShoppingCartV2Manifest = classOf[ShoppingCartV2].getName
  val ShoppingCartV3Manifest = classOf[ShoppingCartV3].getName

  implicit val itemV1Fmt = Json.format[ItemV1]
  implicit val itemV2Fmt = Json.format[ItemV2]
  implicit val itemV3Fmt = Json.format[ItemV3]
  implicit val shoppingCartV1Fmt = Json.format[ShoppingCartV1]
  implicit val shoppingCartV2Fmt = Json.format[ShoppingCartV2]
  implicit val shoppingCartV3Fmt = Json.format[ShoppingCartV3]
  val serializationCharset = Charset.forName("UTF-8")


  // has to be unique
  // http://doc.akka.io/docs/akka/current/scala/serialization.html#SerializerwithStringManifest
  override def identifier: Int = 51109916

  // The manifest (type hint) that will be provided in the fromBinary method
  override def manifest(o: AnyRef): String = o match {
    case _: ShoppingCartV1 => ShoppingCartV1Manifest
    case _: ShoppingCartV2 => ShoppingCartV2Manifest
    case _: ShoppingCartV3 => ShoppingCartV3Manifest
  }

  override def toBinary(o: AnyRef): Array[Byte] = o match {
    case v1: ShoppingCartV1 => Json.stringify(Json.toJson(v1)).getBytes(serializationCharset)
    case v2: ShoppingCartV2 => Json.stringify(Json.toJson(v2)).getBytes(serializationCharset)
    case v3: ShoppingCartV3 => Json.stringify(Json.toJson(v3)).getBytes(serializationCharset)
  }

  def parse[A <: AnyRef](bytes: Array[Byte])(implicit reads: Reads[A]): AnyRef = {
    val result = Json.fromJson[A](Json.parse(new String(bytes, serializationCharset)))
    result.getOrElse(throw new RuntimeException(s"Unable to deserialize $result"))
  }

  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = manifest match {
    case ShoppingCartV1Manifest => parse[ShoppingCartV1](bytes)
    case ShoppingCartV2Manifest => parse[ShoppingCartV2](bytes)
    case ShoppingCartV3Manifest => parse[ShoppingCartV3](bytes)
  }

}
