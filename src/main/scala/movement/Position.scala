package movement

import events.Entity
import movement.Direction.Direction

/**
  * Created by rob on 26/04/16.
  */
case class Position(x: Int, y: Int, blocked: Option[Int] = None) extends Entity {
  val id = -1
  def x(f: Int => Int): Position = copy(x = f(x))
  def y(f: Int => Int): Position = copy(y = f(y))
  def block(e: Int) = copy(blocked = Some(e))
  def unblock(e: Int) = {
    copy(blocked = blocked.filterNot(_ == e))
  }
  def ~= (pos: Position) = pos.x == x && pos.y == y
}

object Position {
  def movePos(dir: Direction)(pos: Position): Position = dir match {
    case Direction.Up => pos.y(_ - 1)
    case Direction.Down => pos.y(_ + 1)
    case Direction.Right => pos.x(_ + 1)
    case Direction.Left => pos.x(_ - 1)
  }
}
