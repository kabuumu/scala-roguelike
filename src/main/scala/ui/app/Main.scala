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

  val canvas = new Canvas(544, 544)
  val frameRate = 1
  //200ms
  var lastDelta = 0
  var keyCode: KeyCode = null
  var state: GameState = GameState(
    player(playerID, initiative = 18, x = 0, y = 0)
      ++ wall(2, 0)
      ++ wall(2, 1)
      ++ wall(2, 2)
      ++ enemy(36, 5, 5)
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
    state = state.processEvents(
      Seq(Async.update) ++ Input(keyCode))
      .processEvents(Seq(Collision.collisionDetector))
    Output.update(state, canvas)
    keyCode = null
  }.start()

  stage.show()
}

