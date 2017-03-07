package core.refactor.system

import core.refactor.entity.Entity
import core.refactor.event.{Event, UpdateEntity, UpdateEntity$}

import scala.annotation.tailrec

/**
  * Created by rob on 03/03/17.
  */
case class GameState(entities: Iterable[Entity]) extends Iterable[Entity] {
  @tailrec
  final def update(events: Iterable[Event]): GameState = {
    entities
      .map(applyEvents(events))
      .unzip match {
      case (newEntities, newEvents) =>
        val state = new GameState(newEntities)

        newEvents.flatten match {
          case Nil => state
          case flattenedEvents => state.update(flattenedEvents)
        }
    }
  }

  override def iterator: Iterator[Entity] = entities.iterator

  private def applyEvents(events: Iterable[Event]) = (entity: Entity) => {
    val startingState = (entity, Seq.empty[Event])
    events
      .collect{case e:UpdateEntity if e.predicate(entity) => e}
      .foldLeft(startingState) {
        case ((previousEntity, previousEvents), event) =>
          val (updatedEntity, newEvents) = event.f(this, previousEntity)
          (updatedEntity, previousEvents ++ newEvents)
      }
  }
}
