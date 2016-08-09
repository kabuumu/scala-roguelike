package rogueLike.movement

import java.util.UUID

import core.Entity
import rogueLike.async.Initiative
import rogueLike.movement.Direction.Direction

/**
  * Created by rob on 26/04/16.
  */
case class Position(x: Int, y: Int, facing: Direction, blocker: Boolean = false, id: String = UUID.randomUUID().toString) extends Entity {
  def initiative(f: Initiative => Initiative) = this

  def x(f: Int => Int): Position = copy(x = f(x))
  def y(f: Int => Int): Position = copy(y = f(y))

  def move(dir: Direction = facing): Position = dir match {
    case Direction.Up => y(_ - 1)
    case Direction.Down => y(_ + 1)
    case Direction.Right => x(_ + 1)
    case Direction.Left => x(_ - 1)
  }
}
