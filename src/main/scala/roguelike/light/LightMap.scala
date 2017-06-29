package roguelike.light

import core.entity.{Entity, ID}
import core.event.EventBuilder._
import core.event.{Event, EventComponent, Update}
import data.GameData
import roguelike.movement.Position
import roguelike.movement.lineofsight.{BresenhamLine, VisibleTiles}

import scala.annotation.tailrec

/**
  * Created by rob on 28/06/17.
  */
case class LightMap(values: Map[Position, Double] = Map.empty) extends EventComponent {
  override val entityEvents: (Entity) => Iterable[Event] = e => Seq(
    new Update(
      predicate = matches(e),
      f = {
        case (state, entity) =>
          val visibleTiles = entity[VisibleTiles]
          val newMap = LightMap.calculateMap(state.entities, visibleTiles.tiles)
          val updatedEntity = entity.update[LightMap](_ => LightMap(newMap))

          (updatedEntity, Nil)
      }
    )
  )
}

object LightMap {

  import LightCaster._

  def calculateMap(entities: Iterable[Entity], visibleTiles: Set[Position]): Map[Position, Double] = {
    val blockers = (entities collect { case e if e.has[LightBlocker] => e[Position] -> e[LightBlocker] }).toMap
    val lightSources = for {
      entity <- entities
      position <- entity.get[Position]
      lightCaster <- entity.get[LightCaster]
    } yield position -> lightCaster

    visibleTiles map { target =>
      val lightValues = lightSources map { case (origin, lightCaster) =>
        val line = BresenhamLine(origin, target)

        @tailrec
        def getValue(line: Iterator[Position], brightness: Double): Double = {
          line.next() match {
            case _ if brightness <= minLightLevel => 0
            case `target` => brightness
            case pos =>
              val decrease = blockers.get(pos).fold(lightDecrease)(_.value * lightDecrease)
              val newValue = brightness * decrease

              getValue(line, newValue)
          }
        }

        getValue(line, lightCaster.brightness)
      }

      target -> lightValues.sum
    } toMap
  }
}
