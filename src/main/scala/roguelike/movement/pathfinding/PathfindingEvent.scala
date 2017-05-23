package roguelike.movement.pathfinding

import core.entity.Entity
import core.event.Update
import roguelike.actors.Actor
import roguelike.movement.{Blocker, Direction, Position}
import core.event.EventBuilder._


/**
  * Created by rob on 16/05/17.
  */
object PathfindingEvent {
  def apply(target: Entity)(entity: Entity) = new Update(
    predicate = matches(entity),
    f = {
      case (state, origin) =>
        val event = for {
          Position(x, y) <- origin[Position]
          Position(targetX, targetY) <- target[Position]
        } yield {
          val blockers =
            (state.entities.seq filter(_[Blocker].isDefined) flatMap(_[Position]) map(pos => (pos.x, pos.y))).toSet
          val pathfinder = new Pathfinder(x -> y, targetX -> targetY, blockers)

          val (newX, newY) = pathfinder.getNext

          Actor.actorMove(Direction(x, y, newX, newY))(origin)
        }
        (origin, event.toSeq)
    }
  )
}
