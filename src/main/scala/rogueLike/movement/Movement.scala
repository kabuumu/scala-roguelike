package rogueLike.movement

import core.EventLock.lockingEvent
import core.{Event, EventLock}
import rogueLike.movement.Direction._

/**
  * Created by rob on 01/07/16.
  */
object Movement {
  def moveEvent(id: String, dir: Direction) = Event {
    case (e: Position) if e.id == id =>
      moveFunction(id, e, dir)
  }

  private def moveFunction(id: String, e: Position, dir: Direction) = {
    val newPos: Position = if (e.facing == dir) e.move(dir) else e.copy(facing = dir)
    val f = Event { case e: Position if e.id == id => (Iterable(newPos), Nil) }

    (Iterable(e.copy(facing = dir)), Seq(withCheckCollision(newPos, e, id, f)))
  }

  private def withCheckCollision(newPos: Position, oldPos: Position, id: String, f: Event) =
    lockingEvent(newPos, id, Seq(f, unblockPosition(oldPos)))

  private def unblockPosition(pos: Position) =
    Event { case lock: EventLock => (Iterable(lock - pos), Nil) }

  def moveEvent(id: String, pos: Position) = Event {
    case e: Position if e.id == id =>
      moveFunction(id, e, getDirection(e, pos))
  }

  private def getDirection(origin: Position, target: Position): Direction = {
    (origin, target) match {
      case (Position(x1, y1, _, _, _), Position(x2, y2, _, _, _)) =>
        val xDiff = x1 - x2
        val yDiff = y1 - y2

        if (xDiff.abs > yDiff.abs) if (xDiff > 0) Left
        else Right
        else if (yDiff > 0) Up
        else Down
    }
  }
}
