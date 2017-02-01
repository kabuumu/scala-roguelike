package core.refactor

import core.GameState

/**
  * Created by rob on 29/06/16.
  */
case class UpdateHandler(entities: Iterable[Component]) {
  def update: GameState = {
    val events = getUpdate

    val updated = entities.map{entity =>
      events
        .filter(_.target == entity)
        .foldLeft(entity)((entity, event) => event(entity))
    }

    GameState(Nil)
  }

  def getUpdate: Seq[EntityUpdate[Component]] = Nil

  trait Event {
    val target: Component
    def apply(e: Component): Component
  }
}

