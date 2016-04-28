package main

import input.Input
import state._

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
  val canvas = new Canvas(512, 512)

  var lastDelta = 0
  var keyCode:KeyCode = null
  var state:GameState = GameState(
    Set(
      Actor(
        Position(5, 5),
        Map(
          (INITIATIVE, Attribute(10)),
          (HEALTH, Attribute(10))
        ),
        isPC = true
      ),
      Actor(
        Position(3, 3),
        Map(
          (INITIATIVE, Attribute(5)),
          (HEALTH, Attribute(10))
        ),
        isPC = false)
    ),
    10
  )
  val frameRate = 1 //200ms

  stage = new PrimaryStage {
    title = "scala-roguelike"
    scene = new Scene {
      content = canvas

      onKeyPressed = {
        key:KeyEvent => keyCode = key.code
      }
    }
  }

  AnimationTimer { now: Long =>
    //if (now - lastDelta >= frameRate) {
      state = state.update(Input(keyCode))
      Output.update(state, canvas)
      if(state.pcTimer == 0) keyCode = null
    //}
  }.start()

  stage.show()
}

