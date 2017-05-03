package refactor.roguelike.movement

import refactor.core.entity.Component
import refactor.roguelike.movement.Direction.Direction

/**
  * Created by rob on 28/04/17.
  */
case class Facing(dir: Direction) extends Component

object Facing {
  def changeFacing(dir: Direction): Facing => Facing = _ => Facing(dir)
}