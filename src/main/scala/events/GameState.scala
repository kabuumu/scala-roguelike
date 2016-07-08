package events

import scala.annotation.tailrec

/**
  * Created by rob on 29/06/16.
  */
case class GameState(entities: Seq[Entity]) {

  def processEvents(events: Seq[Event]): GameState = {

    @tailrec
    def eventLoop(entities: Seq[Entity], events:Seq[Event]): Seq[Entity] = {
      if(events.nonEmpty) {
        val (newEntities, newEvents) = processIteration(entities, events)
        eventLoop(newEntities, newEvents)
      }
      else entities
    }

    def processIteration(entities:Seq[Entity], events: Seq[Event]):(Seq[Entity], Seq[Event]) = {
      entities.par.map { entity => val entityID = entities.indexOf(entity)
        events
        .filter(_.id == entityID)
        .foldLeft((entity, Seq[Event]())) {
          case ((e, s), f) =>
            val (newE, newS) = f(this, e)
            (newE, newS ++ s)
        }
      }.foldLeft((Seq[Entity](), Seq[Event]())) {
        case ((ens, evs), (en, ev)) =>
          (ens :+ en, evs ++ ev)
      }
    }

    copy(entities = eventLoop(entities, events))
  }
}