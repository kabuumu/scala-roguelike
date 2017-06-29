package roguelike.light

import core.entity.Component
import roguelike.movement.Position
import roguelike.movement.lineofsight.BresenhamLine

import scala.annotation.tailrec

/**
  * Created by rob on 19/06/17.
  */
case class LightCaster(brightness: Double) extends Component {
  lazy val range = calculateRange(brightness, 0)

  private def calculateRange(brightness: Double = brightness, count: Int = 0): Int = {
    if(brightness <= LightCaster.minLightLevel) count
    else calculateRange(brightness * LightCaster.lightDecrease, count + 1)
  }
}

object LightCaster {
  val minLightLevel = 0.1
  val lightDecrease = 0.9

  def getLightLevel(caster: LightCaster, origin: Position, target: Position, blockers: Set[(Position, LightBlocker)]): Double = {
    val line = BresenhamLine(origin, target)

    @tailrec
    def getValue(line: Iterator[Position], brightness: Double): Double = {
      line.next() match {
        case _ if brightness <= minLightLevel => 0
        case `target` => brightness
        case pos =>
          val decreases = blockers.collect { case (`pos`, LightBlocker(value)) => value} + lightDecrease
          val newValue = decreases.fold(brightness)(_ * _)

          getValue(line, newValue)
      }
    }

    getValue(line, caster.brightness)
  }
}