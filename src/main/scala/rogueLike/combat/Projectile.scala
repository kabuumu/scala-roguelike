package rogueLike.combat

import java.util.UUID

import core.{Entity, Event, EventLock}
import rogueLike.async.Initiative
import rogueLike.movement.Direction.Direction
import rogueLike.movement.{Direction, Movement, Position}

/**
  * Created by rob on 26/07/16.
  */
case class Projectile (id: String = UUID.randomUUID().toString,
                      timer: Int = Expires.DEFAULT)
  extends Entity with Expires{

  override def timer(f: (Int) => Int): Projectile = copy(timer = f(timer))
}

object Projectile {
  def updateProjectile(id: String) = Event {
    case e: Projectile if e.id == id && e.timer == 0 => (Nil, Seq(EventLock.unlock(e))) //TODO: Move delete code into separate class
    case e: Initiative if e.id == id => (Seq(e.reset), Nil)
    case e: Position if e.id == id => (Seq(e.move()), Nil)
  }
}
