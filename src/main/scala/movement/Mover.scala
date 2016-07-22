package movement

import core.Entity
import movement.Direction.Direction
import movement.Position._

/**
  * Created by rob on 21/06/16.
  */
trait Mover extends Entity{
  val pos: Position

  val facing: Direction

  def pos(f: Position => Position): Mover

  def facing(dir: Direction): Mover

  def move(dir: Direction): Mover = pos(_.move(dir))
}