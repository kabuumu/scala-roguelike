package state

import movement.Direction.Direction
import movement.{Mover, Position}

case class Actor(override val id: Int, pos: Position, facing: Direction) extends Mover {
  override def pos(f: Position => Position) = copy(pos = f(pos))
  override def facing(dir: Direction) = copy(facing = dir)
}
