package state

import core.Initiative
import movement.Direction.Direction
import movement.{Mover, Position}

case class Actor(pos: Position, override val initiative: Initiative, facing: Direction) extends Mover {
  val id = "pc"

  override def pos(f: Position => Position) = copy(pos = f(pos))

  override def initiative(f: Initiative => Initiative) = copy(initiative = f(initiative))

  override def facing(dir: Direction): Actor = copy(facing = dir)
}
