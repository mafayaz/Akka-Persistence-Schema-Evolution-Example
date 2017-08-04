# Akka Schema Evolution with Shopping Carts and Event Adapters #
This example is intended to demonstrate how to perform Schema Evolution with and without Lagom framework (Play-json).

In the following example, we use Json as our encoding mechanism  which only performs encoding and does not support schema evolution 

Here is what the set up looks like when persisting on the latest commit:
`Shopping Cart Actor -> Persist Message Vn -> Event Adapter (pass through) -> Serializer -> Journal`

## Details ##
We configure Akka to perform serialization and event adapters via the configuration
```hocon
akka {
  persistence {
    journal {
      plugin = "akka.persistence.journal.leveldb"
      leveldb {
        event-adapters {
          shoppingCartAdapter = "com.experiments.calvin.eventadapters.ShoppingCartEventAdapter"
        }

        event-adapter-bindings {
          // V1 ~> V3
          "com.experiments.calvin.models.ShoppingCartV1" = shoppingCartAdapter
          // V2 ~> V3
          "com.experiments.calvin.models.ShoppingCartV2" = shoppingCartAdapter
        }
      }
    }
  }

  actor {
    serializers {
      shoppingCart = "com.experiments.calvin.serialization.ShoppingCartSerializer"
    }

    serialization-bindings {
      "com.experiments.calvin.models.ShoppingCartV1" = shoppingCart
      "com.experiments.calvin.models.ShoppingCartV2" = shoppingCart
      "com.experiments.calvin.models.ShoppingCartV3" = shoppingCart
    }
  }
}
```
Notice the event adapter configuration is placed within the journal section. In this case, 
we use the LevelDB journal so we place our Event Adapter configuration in the LevelDB 
section.

For this example, we choose to use a Shopping Cart. We evolve the Shopping
Cart over time and we need to deal with the old events persisted in the
journal and promote them to new version. We add a description field to
each of the items which in turn requires a new version of the Shopping Cart.
Also we update the name of one the fields.

We use the serializer to serialize and deserialize different versions of
the Shopping Cart. The Event Adapter handles the promotion of events from
V1 to V3, and from V2 to V3.


## Credits ##
- [Akka Schema Evolution Example](https://github.com/calvinlfer/Akka-Persistence-Schema-Evolution-Example)
- [Akka Schema Evolution](http://doc.akka.io/docs/akka/current/scala/persistence-schema-evolution.html)
- [Akka Serialization Test](https://github.com/dnvriend/akka-serialization-test)
- [BooPickle](https://github.com/ochrons/boopickle)
