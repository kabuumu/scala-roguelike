package ui.input

import core.entity.Entity
import core.event.Event
import roguelike.actors.Actor._
import roguelike.async.Initiative.isReady
import roguelike.combat.Attack._
import roguelike.movement.Direction.Direction
import roguelike.movement.{Facing, Position}
import ui.input.InputState._

/**
  * Created by rob on 22/04/16.
  */

class InputController {
  var target: Option[Position] = None
  private var inputState: InputState = Game

  def getInputEvents(entity: Entity, keyEventConsumer: KeyEventConsumer): Iterable[Event] = {
    import keyEventConsumer._

    inputState match {
      case Game => if (entity exists isReady) lastInput flatMap {
        case dir: Direction => Some(actorMove(dir)(entity))
        case AttackInput =>
          inputState = TargetSelect
          target = Some(entity[Position].move(entity[Facing].dir))
          None
        case _ => None
      } else Nil
      case TargetSelect =>
        lastInput match {
          case Some(AttackInput) =>
            inputState = Game
            val event = Some(attackEvent(entity, target))
            target = None
            event
          case Some(dir: Direction) =>
            val newPos = target map (_.move(dir))
            if ((newPos exists (_.sqDistance(entity[Position]) <= 2)) && !(newPos contains entity[Position])) target = newPos
            None
          case _ =>
            None
        }
    }
  }
}