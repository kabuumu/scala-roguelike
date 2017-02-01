package updated.movement

import core.refactor.{Component, Entity}

/**
  * Created by rob on 17/01/17.
  */
case class Position(x: Int, y: Int)(val id: Entity) extends Component
