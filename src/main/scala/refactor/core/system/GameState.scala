package refactor.core.system

import refactor.core.entity.{Entity, ID}
import refactor.core.event.{CreateEntity, DeleteEntity, Event, Update}

/**
  * Created by rob on 03/03/17.
  */
case class GameState(entities: Iterable[Entity]) extends Iterable[Entity] {
  def update(events: Iterable[Event]): GameState =
    applyUpdates(events).withCreations(events).withDeletions(events)

  private final def applyUpdates(events: Iterable[Event]): GameState = entities
      .map(applyEvents(events))
      .unzip match {
      case (newEntities, newEvents) =>
        val state = GameState(newEntities)

        newEvents.flatten match {
          case Nil => state
          case flattenedEvents => state.update(flattenedEvents)
        }
    }

  def withCreations(events: Iterable[Event]): GameState =
    ++(events collect { case CreateEntity(entity) => entity })


  private def withDeletions(events: Iterable[Event]): GameState = {
    val deletions: Iterable[ID] = events.collect{case DeleteEntity(entity) => entity[ID]}.flatten

    val updatedEntities = entities filterNot(entity => deletions exists entity[ID].contains)

    GameState(updatedEntities)
  }

  def ++(newEntities: Iterable[Entity]): GameState = {
    GameState(entities ++ newEntities)
  }


  override def iterator: Iterator[Entity] = entities.iterator

  private def applyEvents(events: Iterable[Event]) = (entity: Entity) => {
    val startingState = (entity, Seq.empty[Event])
    events
      .collect { case e: Update if e.predicate(entity) => e }
      .foldLeft(startingState) {
        case ((previousEntity, previousEvents), event) =>
          val (updatedEntity, newEvents) = event.f(this, previousEntity)
          (updatedEntity, previousEvents ++ newEvents)
      }
  }
}
