package rogueLike.movement

import core.Event
import rogueLike.movement.Direction._

/**
  * Created by rob on 01/07/16.
  */
object Movement {
  def moveEvent(id: String, dir: Direction) = Event {
    case (e: Position) if e.id == id =>
      moveFunction(e, dir)
  }

  def moveFunction(e: Position, dir: Direction): (Iterable[Position], Nil.type) = {
    val newPos: Position = if (e.facing == dir) e.move(dir) else e

    (Iterable(newPos.copy(facing = dir, previous = Some(e.copy(previous = None)))), Nil)
  }

  def moveEvent(id: String, pos: Position) = Event {
    case e: Position if e.id == id =>
      moveFunction(e, getDirection(e, pos))
  }

  def getDirection(origin: Position, target: Position): Direction = {
    (origin, target) match {
      case (Position(x1, y1, _, _, _, _), Position(x2, y2, _, _, _, _)) =>
        val xDiff = x1 - x2
        val yDiff = y1 - y2

        if (xDiff.abs > yDiff.abs) if (xDiff > 0) Left
        else Right
        else if (yDiff > 0) Up
        else Down
    }
  }
}
