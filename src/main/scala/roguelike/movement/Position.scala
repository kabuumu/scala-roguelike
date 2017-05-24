package roguelike.movement

import java.lang.Math._

import core.entity.Component
import roguelike.movement.Direction.Direction

/**
  * Created by rob on 09/03/17.
  */
case class Position (x: Int, y: Int) extends Component {
  def x(f: Int => Int): Position = copy(x = f(x))
  def y(f: Int => Int): Position = copy(y = f(y))

  def move(dir: Direction): Position = dir match {
    case Direction.Up => y(_ - 1)
    case Direction.Down => y(_ + 1)
    case Direction.Right => x(_ + 1)
    case Direction.Left => x(_ - 1)
  }

  def distance(pos: Position) = abs(x - pos.x) + abs(y - pos.y)
}

object Position {
  val x: Position => Int = _.x
  val y: Position => Int = _.y
  def move(direction: Direction): Position => Position = _.move(direction)
}
