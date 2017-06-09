package ui.input

import roguelike.movement.Direction
import ui.input.KeyEventConsumer.InputKey

import scalafx.scene.input.{KeyCode, KeyEvent}

/**
  * Created by rob on 07/06/17.
  */
class KeyEventConsumer(keyCodes: Set[KeyCode] = Set.empty,
                       var lastKeyTyped: Option[KeyCode] = None
                      ) {
  def +(event: KeyEvent) = new KeyEventConsumer(keyCodes + event.code, lastKeyTyped)
  def -(event: KeyEvent) = if(keyCodes.contains(event.code)) {
    new KeyEventConsumer(keyCodes - event.code, Some(event.code))
  }
  else this

  def inputs: Set[InputKey] = keyCodes flatMap inputMap.get
  def lastInput: Option[InputKey] = {
    val input = lastKeyTyped flatMap inputMap.get
    lastKeyTyped = None
    input
  }

  private val inputMap = Map(
    KeyCode.Up -> Direction.Up,
    KeyCode.Down -> Direction.Down,
    KeyCode.Left -> Direction.Left,
    KeyCode.Right -> Direction.Right,
    KeyCode.A -> AttackInput
  )
}

object KeyEventConsumer {
  trait InputKey
}
