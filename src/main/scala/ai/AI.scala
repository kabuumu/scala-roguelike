package ai

import input.Input
import movement.Direction
import state.{Actor, GameState}

/**
  * Created by rob on 28/04/16.
  */
object AI {
  def getCommand(actor: Actor, state: GameState): Input = {
    val yDiff = actor.pos.y - state.getHero.map(_.pos.y).getOrElse(actor.pos.y)
    val xDiff = actor.pos.x - state.getHero.map(_.pos.x).getOrElse(actor.pos.x)

    Math.abs(yDiff) > Math.abs(xDiff) match {
      case true => (yDiff < 0, yDiff > 0) match {
        case (true, false) => Direction.Down
        case (false, true) => Direction.Up
        case (_, _) => input.NONE
      }
      case false => (xDiff < 0, xDiff > 0) match {
        case (true, false) => Direction.Right
        case (false, true) => Direction.Left
        case (_, _) => input.NONE
      }
    }
  }
}