package roguelike.combat

import core.entity.Component

/**
  * Created by rob on 13/06/17.
  */
case class Weapon(range: Int = 0, projectileSpeed: Int = 0, attackSpeed: Int) extends Component
