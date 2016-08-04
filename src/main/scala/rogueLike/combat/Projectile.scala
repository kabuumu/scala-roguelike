package rogueLike.combat

import java.util.UUID

import core.{Entity, Event, EventLock}
import rogueLike.async.{HasInitiative, Initiative}
import rogueLike.movement.Direction.Direction
import rogueLike.movement.{Movement, Mover, Position}

/**
  * Created by rob on 26/07/16.
  */
case class Projectile(pos: Position,
                      facing: Direction,
                      override val initiative: Initiative,
                      id: String = UUID.randomUUID().toString,
                      timer: Int = Expires.DEFAULT)
  extends Mover with Entity with HasInitiative with Expires{

  val isBlocker = false

  override def facing(dir: Direction): Projectile = copy(facing = dir)

  override def pos(f: (Position) => Position): Projectile = copy(pos = f(pos))

  override def initiative(f: (Initiative) => Initiative): Projectile = copy(initiative = f(initiative))

  override def timer(f: (Int) => Int): Projectile = copy(timer = f(timer))
}

object Projectile {
  def updateProjectile(id: String) = Event {
    case e: Projectile if e.id == id =>
      if(e.timer==0) (Nil, Seq(EventLock.unlock(e))) //TODO: Move delete code into separate class
      else (Iterable(e.timer(_ - 1).initiative(_.reset)), Iterable(Movement.moveEvent(id, e.facing)))
  }
}
