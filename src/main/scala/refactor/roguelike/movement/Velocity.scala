package refactor.roguelike.movement

import refactor.core.entity.Component
import refactor.roguelike.movement.Direction.Direction
import refactor.roguelike.movement.Velocity._

/**
  * Created by rob on 24/04/17.
  */
case class Velocity (speed: Int, direction: Direction, timer: Int = TimerMax) extends Component

object Velocity {
  val TimerMax = 100
  val velocityCount: Velocity => Velocity = v => v.copy(timer = v.timer - v.speed)
  val resetVelocity: Velocity => Velocity = _.copy(timer = TimerMax)
}
