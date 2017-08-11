package com.experiments.calvin.eventadapters

import akka.persistence.journal.{EventAdapter, EventSeq}
import com.experiments.calvin.models._

/**
  * This class upgrades old events from the event journal
  *
  * actor -> message -> event adapter -> serializer -> journal
  * actor <- message <- event adapter <- serializer <- journal
  */
class ShoppingCartEventAdapter extends EventAdapter {
  // Return the manifest (type hint) that will be provided in the `fromJournal` method.
  // serializer handles this, I only care about classes
  override def manifest(event: Any): String = ""

  // Pass through to serializer since I'm always persisting the latest events
  override def toJournal(event: Any): Any = {
    println(s"Event adapter is sending ${event.getClass.getName} to serializer as passthrough")
    event
  }

  override def fromJournal(event: Any, manifest: String): EventSeq = event match {
    case sc : ShoppingCart =>
      println("Reading ShoppingCart from journal")
      EventSeq(sc)
  }
}
