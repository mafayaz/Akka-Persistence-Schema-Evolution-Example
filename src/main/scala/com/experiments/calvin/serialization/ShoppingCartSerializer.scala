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
  val ShoppingCartManifest = classOf[ShoppingCart].getName

  implicit val itemFmt = Json.format[Item]
  implicit val shoppingCartFmt = Json.format[ShoppingCart]
  val serializationCharset = Charset.forName("UTF-8")


  // has to be unique
  // http://doc.akka.io/docs/akka/current/scala/serialization.html#SerializerwithStringManifest
  override def identifier: Int = 51109916

  // The manifest (type hint) that will be provided in the fromBinary method
  override def manifest(o: AnyRef): String = o match {
    case _: ShoppingCart => ShoppingCartManifest
  }

  override def toBinary(o: AnyRef): Array[Byte] = o match {
    case sc: ShoppingCart => Json.stringify(Json.toJson(sc)).getBytes(serializationCharset)
  }

  def parse[A <: AnyRef](bytes: Array[Byte])(implicit reads: Reads[A]): AnyRef = {
    val result = Json.fromJson[A](Json.parse(new String(bytes, serializationCharset)))
    result.getOrElse(throw new RuntimeException(s"Unable to deserialize $result"))
  }

  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = manifest match {
    case ShoppingCartManifest => parse[ShoppingCart](bytes)
  }

}
