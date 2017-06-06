package roguelike.combat

import core.entity.{Component, Entity}
import core.event.CoreEvents._
import core.event.Event
import core.event.EventBuilder._
import roguelike.actors.attributes.Speed

/**
  * Created by rob on 03/06/17.
  */
trait AttackMode extends Component

case object AttackMode extends AttackMode {
  val setAttackMode: Entity => Event = e => {
    val Speed(speed) = e[Speed]
    (onIDMatch(e)
      update (_ + AttackMode)
      )
  }
}
