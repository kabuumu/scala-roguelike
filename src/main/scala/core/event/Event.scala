package core.event

import core.entity.Entity

/**
  * Created by rob on 07/03/17.
  */
trait Event

object Event {
  type Triggered[E] = Entity => E
}