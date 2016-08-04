package rogueLike.movement

import java.util.UUID

import core.Entity

/**
  * Created by rob on 03/08/16.
  */
case class Wall(pos: Position, id: String = UUID.randomUUID().toString) extends Entity with HasPosition{
  override val isBlocker = true
}
