package combat

import java.util.UUID

import core.{Entity, Event, Initiative}
import movement.Direction.Direction
import movement.{Movement, Mover, Position}

/**
  * Created by rob on 26/07/16.
  */
case class Projectile(pos: Position, facing: Direction, override val initiative: Initiative, id: String = UUID.randomUUID().toString) extends Mover {
  override def facing(dir: Direction): Mover = copy(facing = dir)

  override def pos(f: (Position) => Position): Mover = copy(pos = f(pos))

  override def initiative(f: (Initiative) => Initiative): Entity = copy(initiative = f(initiative))
}

object Projectile {
  def updateProjectile(id: String) = Event {
    case e: Mover if e.id == id => (Iterable(e), Iterable(Movement.moveEvent(id, e.facing)))
  }
}
