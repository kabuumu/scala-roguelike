package core.system

import core.entity.{Entity, ID}
import core.event.{CreateEntity, DeleteEntity, Event, Update}

/**
  * Created by rob on 03/03/17.
  */
case class GameState(entities: Iterable[Entity]) extends Iterable[Entity] {
  def update(events: Iterable[Event]): GameState =
    applyUpdates(events).withCreations(events).withDeletions(events)

  private final def applyUpdates(events: Iterable[Event]): GameState = entities
      .par
      .map(applyEvents(events))
      .unzip match {
      case (newEntities, newEvents) =>
        val state = GameState(newEntities.seq)

        newEvents.flatten match {
          case es if es.isEmpty => state
          case flattenedEvents => state.update(flattenedEvents.seq)
        }
    }

  def withCreations(events: Iterable[Event]): GameState =
    ++(events collect { case CreateEntity(entity) => entity })


  private def withDeletions(events: Iterable[Event]): GameState = {
    val deletions: Iterable[ID] = events.collect{case DeleteEntity(entity) => entity[ID]}

    val updatedEntities = entities filterNot(entity => deletions.exists(_ == entity[ID]))

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
