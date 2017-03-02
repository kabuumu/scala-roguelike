package rogueLike.actors

import core.util.EntityHelpers._
import core.util.EventHelpers._
import core.{Entity, Event}
import rogueLike.async.Initiative
import rogueLike.los.LineOfSight
import rogueLike.movement.Movement._
import rogueLike.movement.MovementHelpers._
import rogueLike.movement.Position

case class Enemy(id: String) extends Entity

object Enemy {
  def update(id: String) =
    Event {
      case (_, init: Initiative) if init.id == id =>
        EventOutput(init.reset)
      case (s, ePos: Position) if ePos.id == id =>
        val event = for {
          target <- s.entities
            .findEntity[Position]().where[Player]()
            .filterNot(target => LineOfSight.losBlocked(ePos, target, s.entities).isDefined)
            .reduceOption(getNearest(ePos))
        } yield moveEvent(ePos.id, getDirection(ePos, target))

        EventOutput(ePos).withEvents(event)
    }
}
