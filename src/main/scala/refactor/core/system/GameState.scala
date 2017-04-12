package refactor.core.system

import refactor.core.entity.Entity
import refactor.core.event.{Event, UpdateEntity}

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
        val state = GameState(newEntities)

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
