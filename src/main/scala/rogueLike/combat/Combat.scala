package rogueLike.combat

import core.util.EntityHelpers._
import core.util.EventHelpers.EventOutput
import core.{Entity, Event}
import rogueLike.async.Initiative
import rogueLike.health.Health
import rogueLike.movement.{Facing, Movement, Position}

/**
  * Created by rob on 21/07/16.
  */
object Combat {
  def projectileEvent(id: String, proj: Iterable[Entity]) = Event {
    case (s, e: Position) if e.id == id =>
      val dir = s.entities.findEntity[Facing](_.id == id).head.direction
      (proj ++ Iterable(e),
        Iterable(Event {
          case (_, pos: Position) if proj.exists(_.id == pos.id) =>
            (Iterable(pos.copy(x = e.x, y = e.y)), Iterable(Movement.moveEvent(pos.id, dir)))
          case (_, facing: Facing) if proj.exists(_.id == facing.id) =>
            EventOutput(facing.copy(direction = dir))
          case (_, e: Initiative) if e.id == id => (Seq(e.reset), Nil)
        })
        )
  }

  def getHit(id: String, attack: Attack) = Health.decreaseHealthEvent(id, attack.damage)
}
