package rogueLike.movement

import core.EventLock.lockingEvent
import core.{Entity, Event, EventLock}
import rogueLike.movement.Direction.Direction

/**
  * Created by rob on 01/07/16.
  */
object Movement {
  def moveEvent(id: String, dir: Direction) = Event {
    case (e: Mover) if e.id == id =>
      val newPos: Position = if (e.facing == dir) e.pos.move(dir) else e.pos
      val f = Event { case e: Mover if e.id == id => (Iterable(e.pos(_ => newPos)), Nil) }

      (Iterable(e.facing(dir)), Seq(withCheckCollision(newPos, e.pos, e, f)))
  }

  private def withCheckCollision(newPos: Position, oldPos: Position, entity: Entity, f: Event) = lockingEvent(newPos, entity, Seq(f, unblockPosition(oldPos, entity)))

  private def unblockPosition(pos: Position, entity: Entity) = Event { case lock: EventLock => (Iterable(lock - pos), Nil) }
}
