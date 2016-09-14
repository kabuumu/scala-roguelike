package rogueLike.movement


import core.Entity
import rogueLike.movement.Direction.Direction

/**
  * Created by rob on 26/04/16.
  */
case class Position(x: Int, y: Int, facing: Direction, isBlocker: Boolean = false, id: String, previous: Option[Position] = None) extends Entity {
  def x(f: Int => Int): Position = copy(x = f(x))
  def y(f: Int => Int): Position = copy(y = f(y))

  def move(dir: Direction = facing): Position = dir match {
    case Direction.Up => y(_ - 1)
    case Direction.Down => y(_ + 1)
    case Direction.Right => x(_ + 1)
    case Direction.Left => x(_ - 1)
  }
}
