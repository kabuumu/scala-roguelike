package movement

import input.Input

/**
  * Created by rob on 21/06/16.
  */
object Direction {
  sealed trait Direction
  object Up extends Direction with Input
  object Down extends Direction with Input
  object Left extends Direction with Input
  object Right extends Direction with Input
}
