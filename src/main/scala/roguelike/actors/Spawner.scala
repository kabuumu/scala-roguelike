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
      for {
        Initiative(_, currentInitiative) <- spawner.get[Initiative]
        if currentInitiative == 0
        position <- spawner.get[Position]
      } yield {
        //TODO - Write this in a better way
        val newEntity = (entity + position + Facing(Direction.Up)).update[ID](_ => new ID)
        onIDMatch(spawner) update Initiative.reset trigger CreateEntity(newEntity)
      }
  }
}
