package com.experiments.calvin.actors

import akka.actor.ActorLogging
import akka.persistence.PersistentActor
import com.experiments.calvin.actors.ShoppingCartActor.GetResult
import com.experiments.calvin.models.ShoppingCart

class ShoppingCartActor extends PersistentActor with ActorLogging {
  var state = ShoppingCart(List.empty)

  val updateState: ShoppingCart => Unit = {
    sc: ShoppingCart => state = sc
  }

  override def persistenceId: String = "shopping-cart-persistent-actor"

  override def receiveRecover: Receive = {
    case sc: ShoppingCart =>
      log.info("recovering Shopping Cart from journal: {}", sc)
      updateState(sc)
  }

  override def receiveCommand: Receive = {
    case sc: ShoppingCart => persist(sc)(updateState)
    case GetResult => sender() ! state
  }
}

object ShoppingCartActor {
  case object GetResult
}