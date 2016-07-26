package combat

import core.Event
import movement.Mover

/**
  * Created by rob on 21/07/16.
  */
object Combat {
  def projectileEvent(id: String, proj: Mover) = Event {
    case e: Mover if e.id == id =>
      (
        Iterable(e, proj
          .pos(_ => e.pos)
          .facing(e.facing)
          .move(e.facing)),
        Nil
        )
  }
}
