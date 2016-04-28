package input

import scalafx.scene.input.KeyCode

/**
  * Created by rob on 22/04/16.
  */
sealed trait Input

object Input {
  def apply(key: KeyCode) = key match{
  case KeyCode.UP => UP
  case KeyCode.DOWN => DOWN
  case KeyCode.LEFT => LEFT
  case KeyCode.RIGHT => RIGHT
  case _ => NONE
}}

case object NONE extends Input
case object UP extends Input
case object DOWN extends Input
case object LEFT extends Input
case object RIGHT extends Input