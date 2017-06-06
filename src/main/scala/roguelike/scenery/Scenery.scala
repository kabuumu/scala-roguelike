package roguelike.scenery

import core.entity.{Component, Entity}

/**
  * Created by rob on 23/05/17.
  */
sealed trait Scenery extends Component

sealed trait Wall extends Scenery

object Wall extends Wall

sealed trait Floor extends Scenery

object Floor extends Floor

object Scenery{
  val isScenery: Entity => Boolean = _.has[Scenery]
}