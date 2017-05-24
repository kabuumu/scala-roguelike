package roguelike.experience

import core.entity.Component
import core.event.Event.Triggered
import core.event.Update
import core.event.CoreEvents._
import core.event.EventBuilder._
import Experience._


/**
  * Created by rob on 24/05/17.
  */
case class Level(current: Int) extends Component

object Level {
  val increaseLevel: Level => Level = old => Level(old.current + 1)

  val levelUpCheck: Triggered[Update] = e =>
    onIDMatch(e) when experienceOverMax update resetExperience update increaseMax update increaseLevel
}
