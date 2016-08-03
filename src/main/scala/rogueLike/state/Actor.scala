package rogueLike.state

import core.Entity
import rogueLike.async.{Initiative, HasInitiative}
import rogueLike.movement.Direction.Direction
import rogueLike.movement.{Mover, Position}

case class Actor(pos: Position, override val initiative: Initiative, facing: Direction) extends Mover with Entity with HasInitiative {
  val id = "pc"

  override val isBlocker = true

  override def pos(f: Position => Position): Actor = copy(pos = f(pos))

  override def initiative(f: Initiative => Initiative): Actor = copy(initiative = f(initiative))

  override def facing(dir: Direction): Actor = copy(facing = dir)
}
