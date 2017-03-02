package rogueLike.movement

import core.Event
import core.util.EntityHelpers._
import core.util.EventHelpers._
import rogueLike.movement.Direction._

/**
  * Created by rob on 01/07/16.
  */
object Movement {
  def moveEvent(id: String, dir: Direction): Event = Event {
    case (s, e: Position) if e.id == id =>
      val facing = s.entities.findEntity[Facing](_.id == e.id).head.direction
      val newPos: Position =
        (if (facing == dir) {
          e.move(dir)
        } else {
          e
        }).copy(previous = Some(e.copy(previous = None)))

      EventOutput(newPos).withEvents(Event{ case (_, e:Facing) if e.id == id => EventOutput(e.copy(direction = facing)) })
  }

  def moveEvent(id: String, pos: Position): Event = Event {
    case (_, e: Position) if e.id == id =>
      EventOutput(e).withEvents(moveEvent(id, getDirection(e, pos)))
  }

  def getDirection(origin: Position, target: Position): Direction = {
    (origin, target) match {
      case (Position(x1, y1, _, _), Position(x2, y2, _, _)) =>
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
