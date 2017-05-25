package roguelike.movement.pathfinding

import core.entity.Entity
import core.event.EventBuilder._
import core.event.Update
import roguelike.actors.Actor
import roguelike.movement.{Blocker, Direction, Position}
import core.event.CoreEvents._
import roguelike.async.Initiative


/**
  * Created by rob on 16/05/17.
  */
object PathfindingEvent {
  def apply(target: Entity)(entity: Entity) = new Update(
    predicate = matches(entity),
    f = {
      case (state, origin) =>
        val Position(x, y) = origin[Position]
        val Position(targetX, targetY) = target[Position]

        val blockers =
          (state.entities.seq filter (_.has[Blocker]) map (_[Position]) map (pos => (pos.x, pos.y))).toSet
        val pathfinder = new Pathfinder(x -> y, targetX -> targetY, blockers)

        val event = pathfinder.getNext match {
          case Some((newX, newY)) => Actor.actorMove(Direction(x, y, newX, newY))(origin)
          case None => onIDMatch(origin) update Initiative.increase(10)
        }

        (origin, Seq(event))
    }
  )
}
