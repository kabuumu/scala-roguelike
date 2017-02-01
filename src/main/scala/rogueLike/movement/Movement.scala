package rogueLike.movement

import core.refactor.EntityUpdate
import core.util.EntityHelpers._
import core.util.EventHelpers._
import core.{Event, GameState}
import rogueLike.movement.Direction._

/**
  * Created by rob on 01/07/16.
  */
object Movement {
  def moveEvent(id: String, dir: Direction) = Event {
    case (_, e: Position) if e.id == id => EventOutput(moveFunction(e, dir))
  }

  def moveFunction(e: Position, dir: Direction): Position = {
    val newPos: Position = if (e.facing == dir) e.move(dir) else e

    newPos.copy(facing = dir, previous = Some(e.copy(previous = None)))
  }

  def moveEvent(id: String, pos: Position) = Event {
    case (_, e: Position) if e.id == id =>
      (Seq(moveFunction(e, getDirection(e, pos))), Nil)
  }

  def move(direction: Direction) = moveFunction(_:Position, direction)

  def getDirection(origin: Position, target: Position): Direction = {
    (origin, target) match {
      case (Position(x1, y1, _, _, _, _), Position(x2, y2, _, _, _, _)) =>
        val xDiff = x1 - x2
        val yDiff = y1 - y2

        if (xDiff.abs > yDiff.abs) {
          if (xDiff > 0) Left else Right
        }
        else {
          if (yDiff > 0) Up else Down
        }
    }
  }
}
