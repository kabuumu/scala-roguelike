package rogueLike.combat

import core.{Entity, Event}
import rogueLike.movement.Position

/**
  * Created by rob on 21/07/16.
  */
object Combat {
  def projectileEvent(id: String, proj: Position) = Event {
    case e: Position if e.id == id =>
      (
        Iterable(e.initiative(_.reset),
          e
          ),
        Nil
        )
  }
}
