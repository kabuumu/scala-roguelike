package state

import movement.{Mover, Position}

case class Actor(id: Int, pos: Position) extends Mover {
  override def pos(f: Position => Position) = copy(pos = f(pos))
}
