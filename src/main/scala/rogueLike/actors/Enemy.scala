package rogueLike.actors

import core.Event._
import core.{Entity, Event}
import rogueLike.async.Initiative
import rogueLike.movement.Movement._
import rogueLike.movement.Position

case class Enemy(id: String) extends Entity

object Enemy {
  def update(id: String) =
    get { case Player(pID) =>
      get { case target: Position if target.id == pID =>
        Event {
          case init: Initiative if init.id == id => (Seq(init.reset), Nil)
          case ePos: Position if ePos.id == id => moveFunction(ePos, getDirection(ePos, target))
        }
      }
    }

  def collide(aID: String, bID: String) = Event {
    case e@Player(`bID`) => (Seq(e), Seq(delete(`bID`)))
  }
}
