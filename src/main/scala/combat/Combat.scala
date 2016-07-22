package combat

import core.{Entity, Event, EventLock}
import movement.{Movement, Mover}

/**
  * Created by rob on 21/07/16.
  */
object Combat {
  def projectileEvent(id: Int, proj: Mover) = Event{
    case e:Mover if e.id == id =>
      (Iterable(e, proj.pos(_ => e.pos).move(e.facing)), Nil)
  }
}
