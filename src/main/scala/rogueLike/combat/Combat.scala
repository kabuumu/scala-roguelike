package rogueLike.combat

import core.{Entity, Event}
import rogueLike.async.Initiative
import rogueLike.health.Health
import rogueLike.movement.{Movement, Position}

/**
  * Created by rob on 21/07/16.
  */
object Combat {
  def projectileEvent(id: String, proj: Iterable[Entity]) = Event {
    case (_, e: Position) if e.id == id =>
      (proj ++ Iterable(e),
        Iterable(Event {
          case (_, pos: Position) if proj.exists(_.id == pos.id) =>
            (Iterable(pos.copy(x = e.x, y = e.y, facing = e.facing)), Iterable(Movement.moveEvent(pos.id, e.facing)))
          case (_, e: Initiative) if e.id == id => (Seq(e.reset), Nil)
        })
        )
  }

  def getHit(id: String, attack: Attack) = Health.decreaseHealthEvent(id, attack.damage)
}
