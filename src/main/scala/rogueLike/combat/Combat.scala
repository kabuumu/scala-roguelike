package rogueLike.combat

import core.{Entity, Event}
import rogueLike.async.HasInitiative
import rogueLike.movement.Mover

/**
  * Created by rob on 21/07/16.
  */
object Combat {
  def projectileEvent(id: String, proj: Mover) = Event {
    case e: Mover with HasInitiative if e.id == id =>
      (
        Iterable(e.initiative(_.reset), proj
          .pos(_ => e.pos)
          .facing(e.facing)
          .move(e.facing)),
        Nil
        )
  }
}
