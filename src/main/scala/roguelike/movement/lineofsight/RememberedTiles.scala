package roguelike.movement.lineofsight

import core.entity.Component
import roguelike.movement.Position

/**
  * Created by rob on 22/05/17.
  */
case class RememberedTiles(tiles: Set[Position] = Set.empty) extends Component

object RememberedTiles {
  def add(tiles: Iterable[Position]): RememberedTiles => RememberedTiles =
    old => RememberedTiles(old.tiles ++ tiles)
}
