package core

import scala.annotation.tailrec

/**
  * Created by rob on 29/06/16.
  */

//TODO: Make GameState constructor private and have companion object apply generate it with nil.
//TODO: Add new predefined event for adding entities
case class GameState(entities: Iterable[Entity]) {
  def processEvents(events: Iterable[Event]): GameState = {
    @tailrec
    def eventLoop(entities: Iterable[Entity], events: Iterable[Event]): Iterable[Entity] = {
      if (events.nonEmpty) {
        val (newEntities, newEvents) = processIteration(entities, events)
        eventLoop(newEntities, newEvents)
      }
      else entities
    }

    def processIteration(entities: Iterable[Entity], events: Iterable[Event]): (Iterable[Entity], Iterable[Event]) = {
      entities.par.map { entity =>
        events
          .filter(_.isDefinedAt(entity))
          .foldLeft((Iterable(entity), Iterable[Event]())) {
            case ((e, s), f) =>
              val (newE, newS) = e.headOption.flatMap(f.lift).getOrElse(Nil, Nil)
              (newE, s ++ newS)
          }
      }.foldLeft((Iterable[Entity](), Iterable[Event]())) {
        case ((ens, evs), (en, ev)) =>
          (ens ++ en, evs ++ ev)
      }
    }

    copy(entities = eventLoop(entities, events))
  }
}