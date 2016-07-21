package core

/**
  * Created by rob on 15/07/16.
  */
class EventLock(map: Map[Entity, Int] = Map()) extends Map[Entity, Int] with Entity{
  override val id: Int = -1

  override def +[B1 >: Int](kv: (Entity, B1)): EventLock = new EventLock((map + kv).asInstanceOf[Map[Entity,Int]])

  override def get(key: Entity): Option[Int] = map.get(key)

  override def iterator: Iterator[(Entity, Int)] = map.iterator

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