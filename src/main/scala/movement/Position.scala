package movement

import core.Entity
import movement.Direction.Direction

/**
  * Created by rob on 26/04/16.
  */
case class Position(x: Int, y: Int) extends Entity {
  val id = -1
  def x(f: Int => Int): Position = copy(x = f(x))
  def y(f: Int => Int): Position = copy(y = f(y))

  def move(dir: Direction): Position = dir match {
    case Direction.Up => y(_ - 1)
    case Direction.Down => y(_ + 1)
    case Direction.Right => x(_ + 1)
    case Direction.Left => x(_ - 1)
  }
}
