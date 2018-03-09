package core.event

import core.event.Event.Triggered
import roguelike.movement.lineofsight.VisibleTiles

/**
  * Created by rob on 05/04/17.
  */
object CoreEvents {
  import core.event.EventBuilder._

  def resetEntity: Triggered[Update] = e => onIDMatch(e) update(_ => e)

  def onIDMatch: Triggered[Update] = event when matches(_)
}
