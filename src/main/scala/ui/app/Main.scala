package ui.app

import core.GameState
import rogueLike.async.Async
import rogueLike.data.Entities._
import rogueLike.movement.Collision
import ui.input.Input

import scala.language.postfixOps
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.{KeyCode, KeyEvent}

/**
  * Created by rob on 13/04/16.
  */
object Main extends JFXApp {
  lazy val playerID: String = "pc"

  val canvas = new Canvas(800, 640)
  val frameRate = 1
  //200ms
  var lastDelta = 0L
  var keyCode: KeyCode = null
  var state: GameState = GameState(
    player(playerID, initiative = 20, x = 0, y = 0)
      ++ wall(2, 0)
      ++ wall(2, 1)
      ++ wall(2, 2)
      ++ enemy(25, 5, 5)
  )
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
      state = state.processEvents(
        Seq(Async.update) ++ Input(keyCode))
        .processEvents(Seq(Collision.collisionDetector))
      new Output(state, canvas).update()
      keyCode = null
      lastDelta = now
    }
  }.start()

  stage.show()
}

