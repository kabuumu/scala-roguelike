package rogueLike.movement

import core.Event
import core.Event._
import core.util.EventHelpers._
import core.util.EntityHelpers._
import rogueLike.actors.{Enemy, Player}
import rogueLike.combat.{Attack, Combat, Projectile}
import rogueLike.health.Health

/**
  * Created by rob on 24/08/16.
  */
object Collision {
  lazy val collisionDetector: Event =
    Event{ case (s, a: Position) =>
      val es = s.entities

      val events = for{
        aBlocker <- es.findEntity[Blocker](_.id == a.id)
        bBlocker <- es.findEntity[Blocker]().where[Position](b => a.id != b.id && (a.x, a.y) == (b.x, b.y))
      } yield collisionEvent(aBlocker.id, bBlocker.id)

      EventOutput(a).withEvents(events)
    }

  def collisionEvent(aID: String, bID: String) = Event {
    case (s, e@Projectile(`bID`)) =>
      val attackEvent = for{
        attackEntity <- s.entities.findEntity[Attack](_.id==`bID`)
      } yield Combat.getHit(aID, attackEntity)

      EventOutput(e) withEvents attackEvent withEvents Event.delete(bID)
    case (s, e: Position) if e.id == aID && s.entities.findEntity[Projectile](_.id==bID).isEmpty =>
      val isPlayer = s.entities.findEntity[Player](_.id == aID)
      val healthDecrease = isPlayer.map(player => Health.decreaseHealthEvent(player.id, 10))
      (Seq(e.previous.getOrElse(e)), healthDecrease)
  }
}

