package movement

import events.Entity
import movement.Direction.Direction
import movement.Position._

/**
  * Created by rob on 21/06/16.
  */
trait Mover [T <: Mover[_]] extends Entity{
  val pos: Position

  def pos(f: Position => Position): T
}

object Mover {
  def move[T <: Mover[_]](dir: Direction)(mover: Mover[T]): T = {
    mover pos(pos => movePos(dir)(pos))
  }

  def unapply(mover: Mover[_]): Option[Position] = Some(mover.pos)
}
