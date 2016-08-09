package core

import java.util.UUID

/**
  * Created by rob on 29/06/16.
  */
trait Entity {
  val id: String
}

object Entity {
  def createEntity(entities: (String => Entity)*)(implicit id: String = UUID.randomUUID().toString) = entities.map(_(id))
}