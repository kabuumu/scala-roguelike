package roguelike.actors

import core.entity.{Entity, ID}
import core.event.EventBuilder._
import core.event.{CreateEntity, Event, EventComponent, Update}
import core.system.GameState
import roguelike.async.Initiative
import roguelike.movement.{Direction, Facing, Position}

/**
  * Created by rob on 15/05/17.
  */
case class Spawner(entity: Entity) extends EventComponent {
  override val entityEvents: (Entity) => Iterable[Event] = spawner =>
    Seq(new Update(predicate = matches(spawner), f = spawnEvent))

  lazy val spawnEvent: ((GameState, Entity)) => (Entity, Seq[Event]) = {
    case (state, spawner) =>
      val Initiative(currentInitiative, _) = spawner[Initiative]
      val pos@Position(x, y) = spawner[Position]

      val surroundingPositions = for {
        x <- x - 1 to x + 1
        y <- y - 1 to y + 1
      } yield Position(x, y)

      val isBlocked = state.entities.collectFirst {
        case e if e.exists[Position](surroundingPositions.contains) && e.exists[Affinity](_ == entity[Affinity]) => e
      }.isDefined

      val (newEntity: Entity, events: Seq[Event]) =
        //TODO - Discover why this isn't working
        if (isBlocked/* || currentInitiative != 0*/) {
          (spawner, Nil)
        }
        else {
          val newEntity = (entity + pos + Facing(Direction.Up)).update[ID](_ => new ID)
          (spawner.update(Initiative.reset), Seq(CreateEntity(newEntity)))
        }

      (newEntity, events)
  }

}
