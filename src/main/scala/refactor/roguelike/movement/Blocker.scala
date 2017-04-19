package refactor.roguelike.movement

import refactor.core.entity.Component

/**
  * Created by rob on 13/04/17.
  */

sealed trait Blocker extends Component

object Blocker extends Blocker
