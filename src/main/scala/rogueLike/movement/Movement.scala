package rogueLike.movement

import core.EventLock.lockingEvent
import core.{Entity, Event, EventLock}
import rogueLike.movement.Direction.Direction

/**
  * Created by rob on 01/07/16.
  */
object Movement {
  def moveEvent(id: String, dir: Direction) = Event {
    case (e: Position) if e.id == id =>
      val newPos: Position = if (e.facing == dir) e.move(dir) else e
      val f = Event { case e: Position if e.id == id => (Iterable(newPos), Nil) }

      (Iterable(e.copy(facing = dir)), Seq(withCheckCollision(newPos, e, id, f)))
  }

  private def withCheckCollision(newPos: Position, oldPos: Position, id: String, f: Event) =
    lockingEvent(newPos, id, Seq(f, unblockPosition(oldPos)))

  private def unblockPosition(pos: Position) =
    Event { case lock: EventLock => (Iterable(lock - pos), Nil) }
}
