package roguelike.movement.lineofsight

import java.lang.Math.abs

import core.entity.Entity
import core.event.CoreEvents._
import core.event.EventBuilder._
import core.event.{Event, EventComponent, Update}
import roguelike.movement.Direction.{apply => _, _}
import roguelike.movement.lineofsight.RememberedTiles._
import roguelike.movement.lineofsight.ShadowCaster.isVisible
import roguelike.movement.lineofsight.VisibleTiles._
import roguelike.movement.{Blocker, Facing, Position}

/**
  * Created by rob on 20/05/17.
  */
case class VisibleTiles(tiles: Set[Position]) extends EventComponent {
  override val entityEvents: (Entity) => Iterable[Event] = entity => {
    Some(new Update(
      predicate = matches(entity),
      f = {
        case (state, origin) =>
          val event: Option[Event] = for {
            Position(x, y) <- origin.get[Position]
            Facing(direction) <- origin.get[Facing]
          } yield {
            val blockers =
              (state.entities.seq filter (_.has[Blocker]) map (_[Position]) map (pos => (pos.x, pos.y))).toSet

            val visibleTiles = for {
              targetX <- x - range to x + range
              targetY <- y - range to y + range
              if isVisible(x, y, targetX, targetY, blockers)
            } yield Position(targetX, targetY)

            onIDMatch(entity) update set(visibleTiles) update add(visibleTiles)
          }
          (origin, event.toSeq)
      }
    ))
  }
}

object VisibleTiles {
  val range = 10

  def set(tiles: Iterable[Position]): VisibleTiles => VisibleTiles = _ => VisibleTiles(tiles.toSet)

  def sq(n: Int) = n * n
}
