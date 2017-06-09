package ui.input

/**
  * Created by rob on 06/06/17.
  */
object InputState {
  sealed trait InputState
  case object Game extends InputState
  case object TargetSelect extends InputState
}
