package rogueLike.movement

import core.Event
import core.Event._
import rogueLike.actors.Enemy
import rogueLike.combat.Projectile

/**
  * Created by rob on 24/08/16.
  */
object Collision {
  lazy val collisionDetector: Event = get { case a: Position if a.isBlocker => a } { a =>
    get { case b: Position if a.id != b.id && (a.x, a.y) == (b.x, b.y) && b.isBlocker => b }
      { b => collisionEvent(a.id, b.id) }
  }

  def collisionEvent(aID: String, bID: String) = Event {
    case e@Projectile(`aID`) => (Seq(e), Seq(Projectile.collide(aID, bID)))
    case e@Enemy(`aID`) => (Seq(e), Seq(Enemy.collide(aID, bID)))
    case e: Position if e.id == aID => (Seq(e.previous.getOrElse(e)), Nil)
  }
}

