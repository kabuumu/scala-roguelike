package roguelike.movement.lineofsight

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
              targetX <- xRange(direction, x)
              targetY <- yRange(direction, y)
              if (direction match {
                case Left | Right => Math.abs(targetY - y) / gradDiv < Math.abs(targetX - x) + gradMod
                case Up | Down => Math.abs(targetX - x) / gradDiv < Math.abs(targetY - y) + gradMod
              })
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
  val gradMod = 1
  val gradDiv = 2

  def set(tiles: Iterable[Position]): VisibleTiles => VisibleTiles = _ => VisibleTiles(tiles.toSet)

  def xRange(direction: Direction, x: Int) = direction match {
    case Up | Down => x - 5 to x + 5
    case Left => x - 10 to x
    case Right => x to x + 10
  }

  def yRange(direction: Direction, y: Int) = direction match {
    case Left | Right => y - 5 to y + 5
    case Down => y to y + 10
    case Up => y - 10 to y
  }
}
