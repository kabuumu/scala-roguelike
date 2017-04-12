package refactor.core.event

import refactor.core.entity.Entity
import refactor.core.event.UpdateEntity._
import refactor.core.system.GameState

/**
  * Created by rob on 03/03/17.
  */
class UpdateEntity private[event](val predicate: Entity => Boolean, val f: Input => Output) extends Event

object UpdateEntity {
  type Input = (GameState, Entity)
  type Output = (Entity, Seq[Event])
}