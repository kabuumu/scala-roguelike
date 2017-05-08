package refactor.core.event

import refactor.core.entity.Entity

/**
  * Created by rob on 07/03/17.
  */
trait Event

object Event {
  type Triggered[E] = Entity => E
}