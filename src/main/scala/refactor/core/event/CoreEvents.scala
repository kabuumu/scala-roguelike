package refactor.core.event

import refactor.core.event.Event.Triggered

/**
  * Created by rob on 05/04/17.
  */
object CoreEvents {
  import refactor.core.event.EventBuilder._

  def resetEntity: Triggered[Update] = e => onIDMatch(e) updateEntity{
    entity =>
      println(entity)
      println(e)
      e
  }

  def onIDMatch: Triggered[Update] = event when matches(_)
}
