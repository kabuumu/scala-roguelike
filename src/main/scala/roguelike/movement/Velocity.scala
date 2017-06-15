package roguelike.movement

import core.entity.Entity
import core.event.CoreEvents._
import core.event.EventBuilder._
import core.event.{DeleteEntity, Event, EventComponent}
import roguelike.combat.Projectile._
import roguelike.movement.Velocity._

/**
  * Created by rob on 24/04/17.
  */
case class Velocity(speed: Int, path: Iterator[Position], timer: Int = TimerMax) extends EventComponent {
  override val entityEvents: Entity => Seq[Event] = entity => Seq(
    onIDMatch(entity)
      when zeroTimer
      update resetVelocity
      update (_.update[Position](_ => path.next()))
      trigger projectileCollision,

    onIDMatch(entity) when activeTimer update velocityCount
  )
}

object Velocity {
  val activeTimer: Velocity => Boolean = _.timer > 0
  val zeroTimer: Velocity => Boolean = _.timer <= 0
  val TimerMax = 100
  val velocityCount: Velocity => Velocity = v => v.copy(timer = v.timer - v.speed)
  val resetVelocity: Velocity => Velocity = _.copy(timer = TimerMax)
}
