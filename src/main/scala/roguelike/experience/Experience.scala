package roguelike.experience

import core.entity.{Component, Entity}
import core.event.Event
import core.event.EventBuilder._
import core.event.CoreEvents._
import Level._

/**
  * Created by rob on 24/05/17.
  */
case class Experience(current: Int = 0, max: Int) extends Component

object Experience {
  def increaseExperienceEvent(amount: Int): Entity => Event = e =>
    onIDMatch(e) update increaseExperience(amount) trigger levelUpCheck
  def increaseExperience(amount: Int): Experience => Experience =
    old => Experience(old.current + amount, old.max)

  def experienceOverMax: Experience => Boolean = exp => exp.current >= exp.max

  val resetExperience: Experience => Experience = e => Experience(e.current - e.max, e.max)

  val increaseMax: Experience => Experience = exp => exp.copy(max = exp.max * 2)
}