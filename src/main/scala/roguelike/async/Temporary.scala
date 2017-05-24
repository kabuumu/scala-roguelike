package roguelike.async

import core.entity.Entity
import core.event.EventBuilder._
import core.event.{EventComponent, Update}
import roguelike.combat.Health._
import core.event.CoreEvents._
import roguelike.combat.Attack

/**
  * Created by rob on 09/05/17.
  */
object Temporary extends EventComponent {
  override val entityEvents: Entity => Seq[Update] = entity =>
    Seq(onIDMatch(entity) update decreaseHealth(Attack(1)))
}
