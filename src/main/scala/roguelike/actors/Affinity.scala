package roguelike.actors

import core.entity.Component
import roguelike.actors.Affinity.Faction

/**
  * Created by rob on 08/05/17.
  */
case class Affinity(faction:Faction) extends Component

object Affinity {
  sealed trait Faction extends Component

  object Enemy extends Faction

  object Player extends Faction

  def hasAffinity(faction: Faction): Affinity => Boolean = _.faction == faction
}
