package core

import java.util.UUID

/**
  * Created by rob on 15/07/16.
  */
class EventLock(map: Map[Entity, String] = Map()) extends Map[Entity, String] with Entity{
  val id = "eventLock"

  override val initiative = Initiative.defaultInitiative

  override def initiative(f: Initiative => Initiative): EventLock = this

  override def +[B1 >: String](kv: (Entity, B1)): EventLock = new EventLock((map + kv).asInstanceOf[Map[Entity,String]])

  override def get(key: Entity): Option[String] = map.get(key)

  override def iterator: Iterator[(Entity, String)] = map.iterator

  override def -(key: Entity): EventLock = new EventLock(map - key)
}

object EventLock{
  def apply() = new EventLock()

  def lockingEvent(key: Entity, entity:Entity, successEvents: Seq[Event] = Nil, failEvents: Seq[Event] = Nil) = Event({
    case lock: EventLock =>
      lock
        .get(key).map(_ => (Iterable(lock), failEvents))
        .getOrElse(Iterable(lock + (key -> entity.id)), successEvents)
    }
  )
}