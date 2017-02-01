package core.util

import core.{Entity, Event}

/**
  * Created by rob on 08/11/16.
  */

object EventHelpers {
  type TupleType = (Iterable[Entity], Iterable[Event])

  implicit class TupleSugar(tuple: TupleType) {
    def withEvents(events: Event*) = tuple.copy(_2 = tuple._2 ++ events)
    def withEvents(events: Iterable[Event]) = tuple.copy(_2 = tuple._2 ++ events)

    def withEntities(entities: Entity*) = tuple.copy(_1 = tuple._1 ++ entities)
    def withEntities(entities: Iterable[Entity]) = tuple.copy(_1 = tuple._1 ++ entities)
  }

  object EventOutput {
    def apply(events: Event*): TupleType = (Nil, events)

    def apply(entities: Entity*)(implicit d: DummyImplicit): TupleType = (entities, Nil)

    def apply(entities: Iterable[Entity] = Nil, events: Iterable[Event] = Nil): TupleType = (entities, events)
  }

}
