package core.refactor

import java.util.UUID

/**
  * Created by rob on 17/01/17.
  */
trait Component extends (Entity => Component) {
  val id: Entity
  override def apply(entity: Entity): Component = new Component{val id = entity}
}

case class Position(x: Int, y: Int)(val id: Entity) extends Component

object Test {
  val test: (Entity) => Position = Position(12, 23)

  Entity(test).foreach{
    case Position(x, y) => print(x + "" + y)
  }
}

