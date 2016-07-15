package movement

import events.{Entity, Event, GameState}
import movement.Direction.Direction

/**
  * Created by rob on 01/07/16.
  */
object Movement {
  def moveEvent(id: Int, dir: Direction) = Event(id, moveFunction(dir, id))

  private def moveFunction(dir: Direction, id: Int): PartialFunction[Entity,(Entity, Seq[Event])] = {
    case (e: Mover[_]) if e.id == id =>
      val newPos: Position = Position.movePos(dir)(e.pos)
      val f = Event(id, {case e:Mover[_] if e.id==id => (e.pos(_ => newPos), Nil)})

      (e, Seq(Event(0, withCheckCollision(newPos, e, f))))
  }

  private def withCheckCollision(pos: Position, entity: Mover[_], f: Event): PartialFunction[Entity,(Entity, Seq[Event])] = {
    case (e: Position) if e ~= pos =>
      if(e.blocked.isDefined) (e, Nil)
      else (e.block(entity.id), Seq(f, unblockPosition(entity.pos, entity)))
  }

  private def unblockPosition(pos: Position, entity: Entity) = Event(0,
    {case (e: Position) if e ~= pos =>
      (e.unblock(entity.id), Nil)
    }
  )
}
