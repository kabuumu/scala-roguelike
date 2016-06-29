package movement

import movement.Direction.Direction

/**
  * Created by rob on 26/04/16.
  */
case class Position(x: Int, y: Int) {
  def x(f: Int => Int): Position = copy(x = f(x))
  def y(f: Int => Int): Position = copy(y = f(y))
}

object Position {
  def movePos(dir: Direction)(pos: Position): Position = dir match {
    case Direction.Up => pos.y(_ - 1)
    case Direction.Down => pos.y(_ + 1)
    case Direction.Right => pos.x(_ + 1)
    case Direction.Left => pos.x(_ - 1)
  }
}
