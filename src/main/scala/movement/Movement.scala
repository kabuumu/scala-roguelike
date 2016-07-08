package movement

import events.{Entity, Event, GameState}
import movement.Direction.Direction

/**
  * Created by rob on 01/07/16.
  */
object Movement {
  def moveEvent(id: Int, dir: Direction) = Event(id, moveFunction(dir, id))

//  def moveFunction(dir: Direction): (GameState, Entity) => (Entity, Seq[Event]) = {
//    case (s, e: Mover[_]) =>
//      val newPos: Position = Position.movePos(dir)(e.pos)
//      val collision = s.entities.collectFirst{ case e: Mover[_] if e.pos == newPos => e }
//
//      collision
//        .map(collide(e))
//        .getOrElse((e.pos(_ => newPos), Nil))
//    case (_, e) => (e, Nil)
//  }

  def moveFunction(dir: Direction, id: Int): PartialFunction[(GameState, Entity),(Entity, Seq[Event])] = {
    case (s, e: Mover[_]) =>
      val newPos: Position = Position.movePos(dir)(e.pos)
      val posID = s.entities.indexOf(newPos)
      val f = Event(id, {case (_, e:Mover[_]) => (e.pos(_ => newPos), Nil)})

      (e, Seq(Event(posID, withCheckCollision(f))))
  }

  def withCheckCollision(f: Event): PartialFunction[(GameState, Entity),(Entity, Seq[Event])] = {
    case (_, e: Position) =>
      if(e.blocked) (e, Nil)
      else (e.block, Seq(f))
  }
}
