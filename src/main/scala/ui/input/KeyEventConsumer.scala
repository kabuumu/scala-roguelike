package ui.input

import org.lwjgl.glfw.GLFW._
import roguelike.movement.Direction
import ui.input.KeyEventConsumer.InputKey


/**
  * Created by rob on 07/06/17.
  */
class KeyEventConsumer(val keyCodes: Set[Int] = Set.empty,
                       var lastKeyTyped: Option[Int] = None
                      ) {
  def +(event: Int) = new KeyEventConsumer(keyCodes + event, lastKeyTyped)
  def -(event: Int) = if(keyCodes.contains(event)) {
    new KeyEventConsumer(keyCodes - event, Some(event))
  }
  else this

  def inputs: Set[InputKey] = keyCodes flatMap inputMap.get
  def lastInput: Option[InputKey] = {
    val input = lastKeyTyped flatMap inputMap.get
    lastKeyTyped = None
    input
  }

  private val inputMap = Map(
    GLFW_KEY_UP -> Direction.Up,
    GLFW_KEY_DOWN -> Direction.Down,
    GLFW_KEY_LEFT -> Direction.Left,
    GLFW_KEY_RIGHT -> Direction.Right,
    GLFW_KEY_A -> AttackInput
  )
}

object KeyEventConsumer {
  trait InputKey
}
