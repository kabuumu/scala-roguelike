package roguelike.light

import core.entity.Component
import roguelike.movement.Position
import roguelike.movement.lineofsight.BresenhamLine

import scala.annotation.tailrec

/**
  * Created by rob on 19/06/17.
  */
case class LightCaster(brightness: Double) extends Component

object LightCaster {
  val lightDecrease = 0.9

  def getLightLevel(caster: LightCaster, origin: Position, target: Position, blockers: Set[(Position, LightBlocker)]): Double = {
    val line = BresenhamLine(origin, target)

    @tailrec
    def getValue(line: Iterator[Position], brightness: Double): Double = {
      line.next() match {
        case _ if brightness <= 0.1 => 0
        case `target` => brightness
        case pos =>
          val newValue = ((blockers collect {
            case (`pos`, LightBlocker(value)) => value
          }) + lightDecrease)
            .fold(brightness)(_ * _)

          getValue(line, newValue)
      }
    }

    getValue(line, caster.brightness)
  }
}