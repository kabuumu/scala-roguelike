package movement

import core.Event.EventReturn
import core.{Entity, Event, EventLock, GameState}
import movement.Direction.Direction
import core.EventLock.lockingEvent

/**
  * Created by rob on 01/07/16.
  */
object Movement {
  def moveEvent(id: Int, dir: Direction) = Event{
    case (e: Mover) if e.id == id =>
      val newPos: Position = if(e.facing==dir) e.pos.move(dir) else e.pos
      val f = Event{case e:Mover if e.id==id => (Iterable(e.pos(_ => newPos)), Nil)}

      (Iterable(e.facing(dir)), Seq(withCheckCollision(newPos, e, f)))
  }

  private def withCheckCollision(pos: Position, entity: Mover, f: Event) = lockingEvent(pos, entity, Seq(f, unblockPosition(entity.pos, entity)))

  private def unblockPosition(pos: Position, entity: Entity) = Event{case lock: EventLock => (Iterable(lock - pos), Nil)}
}
