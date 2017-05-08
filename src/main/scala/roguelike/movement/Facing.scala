package roguelike.movement

import core.entity.Component
import roguelike.movement.Direction.Direction

/**
  * Created by rob on 28/04/17.
  */
case class Facing(dir: Direction) extends Component

object Facing {
  def changeFacing(dir: Direction): Facing => Facing = _ => Facing(dir)
}