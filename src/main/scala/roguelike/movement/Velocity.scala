package roguelike.movement

import core.entity.Component
import roguelike.movement.Direction.Direction
import roguelike.movement.Velocity._

/**
  * Created by rob on 24/04/17.
  */
case class Velocity (speed: Int, direction: Direction, timer: Int = TimerMax) extends Component

object Velocity {
  val TimerMax = 100
  val velocityCount: Velocity => Velocity = v => v.copy(timer = v.timer - v.speed)
  val resetVelocity: Velocity => Velocity = _.copy(timer = TimerMax)
}
