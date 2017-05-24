package roguelike.actors

import core.entity.{Entity, ID}
import core.event.{CreateEntity, Event, EventComponent}
import roguelike.async.Initiative
import roguelike.movement.{Direction, Facing, Position}
import core.event.CoreEvents._
import core.event.EventBuilder._
/**
  * Created by rob on 15/05/17.
  */
case class Spawner(entity: Entity) extends EventComponent{
  override val entityEvents: (Entity) => Iterable[Event] = {
    spawner =>
      for{
        Initiative(_, currentInitiative) <- spawner[Initiative]
        if currentInitiative == 0
        position <- spawner[Position]
      } yield {
        val newEntity = entity + position + Facing(Direction.Up) + new ID
        onIDMatch(spawner) update Initiative.reset trigger CreateEntity(newEntity)
      }
  }
}
