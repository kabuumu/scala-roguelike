package core

/**
  * Created by rob on 29/06/16.
  */
trait Entity {
  val id: String
}

object Entity {
  implicit def asEntity[T >: Entity](e: T): Entity ={
    e.asInstanceOf[Entity]
  }
}