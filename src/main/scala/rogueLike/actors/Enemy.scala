package rogueLike.actors

import core.Event._
import core.{Entity, Event}
import rogueLike.async.Initiative
import rogueLike.movement.{Movement, Position}

case class Enemy(id: String) extends Entity

object Enemy {
  def update(id: String) =
    get { case Player(pid) => pid }(pid =>
      get { case pPos: Position if pPos.id == pid => pPos }(
        _ => Event { case init: Initiative if init.id == id => (Seq(init.reset), Nil) },
        Movement.moveEvent(id, _)
      )
    )

  def collide(aID: String, bID: String) = Event {
    case e@Player(`bID`) => (Seq(e), Seq(delete(`bID`)))
  }
}
