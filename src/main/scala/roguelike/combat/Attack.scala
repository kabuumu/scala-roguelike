package roguelike.combat

import core.entity.{Component, Entity, ID}
import core.event.{CoreEvents, CreateEntity, Event}
import roguelike.async.Temporary
import roguelike.movement.{Facing, Position}

/**
  * Created by rob on 08/05/17.
  */
case class Attack(damage: Int) extends Component

object Attack{
  val swordAttack: Entity => Iterable[Event] = user => {
    for {
      pos <- user[Position]
      Facing(dir) <- user[Facing]
    } yield CreateEntity(Entity(new ID, pos.move(dir), Attack(10), Temporary, Health(3)))
  }
}