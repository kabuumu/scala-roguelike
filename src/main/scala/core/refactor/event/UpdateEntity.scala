package core.refactor.event

import core.refactor.entity.Entity
import core.refactor.event.UpdateEntity.{Input, Output}
import core.refactor.system.GameState

/**
  * Created by rob on 03/03/17.
  */
class UpdateEntity private[event](val predicate: Entity => Boolean, val f: Input => Output) extends Event

object UpdateEntity {
  type Input = (GameState, Entity)
  type Output = (Entity, Seq[UpdateEntity])
}