package rogueLike.movement

import rogueLike.movement.Direction.Direction

/**
  * Created by rob on 21/06/16.
  */
trait Mover {
  val pos: Position

  val facing: Direction

  def pos(f: Position => Position): Mover

  def facing(dir: Direction): Mover

  def move(dir: Direction): Mover = pos(_.move(dir))
}