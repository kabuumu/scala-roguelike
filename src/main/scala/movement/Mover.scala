package movement

import events.Entity
import movement.Direction.Direction
import movement.Position._

/**
  * Created by rob on 21/06/16.
  */
trait Mover extends Entity{
  val pos: Position

  def pos(f: Position => Position): Mover

  def move(dir: Direction): Mover = pos(_.movePos(dir))
}