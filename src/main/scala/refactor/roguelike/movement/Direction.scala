package refactor.roguelike.movement

import ui.input.Input

/**
  * Created by rob on 21/06/16.
  */
object Direction {
  sealed trait Direction
  object Up extends Direction
  object Down extends Direction
  object Left extends Direction
  object Right extends Direction
}
