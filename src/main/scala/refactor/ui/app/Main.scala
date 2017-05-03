package refactor.ui.app

import refactor.core.system.GameState
import refactor.ui.input.Input

import scala.language.postfixOps
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.{KeyCode, KeyEvent}
import refactor.core.entity.{Entity, ID}
import refactor.roguelike.map.MapConverter
import refactor.roguelike.map.MapConverter._
import refactor.roguelike.movement.Direction.Up
import refactor.roguelike.movement.Movement._
import refactor.roguelike.movement._
import rogueLike.actors.Player

/**
  * Created by rob on 13/04/16.
  */
object Main extends JFXApp {
  val canvas = new Canvas(800, 640)
  val frameRate = 1
  //200ms
  var lastDelta = 0L
  var keyCode: KeyCode = _

  val playerID = new ID
  val startingPlayer = Entity(playerID, Position(1, 1), Facing(Up))
  val walls = convert(tileMap)

  var state: GameState = GameState(Seq(
    startingPlayer
  ) ++ walls)

  stage = new PrimaryStage {
    title = "scala-roguelike"
    scene = new Scene {
      content = canvas

      onKeyPressed = {
        key: KeyEvent => keyCode = key.code
      }
    }
  }

  AnimationTimer { now: Long =>
    if(lastDelta < now - 1000000000/150) {
      val inputEvent = for {
        player <- state.entities.find(_[ID] contains playerID)
        inputEvent <- Input(keyCode)
      } yield inputEvent(player)

      state = state.update(Seq(velocityUpdate) ++ inputEvent)

      new Output(state, canvas).update()
      keyCode = null
      lastDelta = now
    }
  }.start()

  stage.show()
}

