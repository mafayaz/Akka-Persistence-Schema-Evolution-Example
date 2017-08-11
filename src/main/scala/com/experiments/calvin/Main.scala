package com.experiments.calvin


import akka.actor.setup.ActorSystemSetup
import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.experiments.calvin.actors.ShoppingCartActor
import com.experiments.calvin.actors.ShoppingCartActor.GetResult
import com.experiments.calvin.models.{Item, ShoppingCart}
import com.experiments.calvin.registry.ShoppingCartSerializerRegistry
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

object Main extends App {

  val shoppingCart = ShoppingCart(
//    List(
//      Item(1, "lays chips")
      Item(3, "caramilk chocolate", "bleh", "dumdidah")
//    )
  )

  implicit val timeout = Timeout(10 seconds)

  val actorSystem = ActorSystem(name = "example", ActorSystemSetup(JsonSerializerRegistry.serializationSetupFor(ShoppingCartSerializerRegistry)))

  val shoppingCartActor = actorSystem.actorOf(Props[ShoppingCartActor], name = "shopping-cart-actor")
  shoppingCartActor ! shoppingCart
  val futureResult = (shoppingCartActor ? GetResult).mapTo[ShoppingCart]
  val obtainedCart = Await.result(futureResult, 10 seconds)
  println(obtainedCart)
  actorSystem.terminate()
}
