package ui.app

import core.entity.ID
import core.event.EventComponent._
import core.system.GameState
import data.GameData._
import roguelike.ai.EnemyAI
import roguelike.async.Initiative._
import roguelike.movement.Position
import ui.input.Input
import ui.output.Output
import ui.output.OutputConfig._

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
  val canvas = new Canvas(canvasWidth, canvasHeight)
  val frameRate = 1
  //200ms
  var lastDelta = 0L
  var keyCode: KeyCode = _

  val output = new Output(canvas)

  var state: GameState = GameState(Seq(
    startingPlayer,
    enemySpawner(enemy, Position(10, 10))
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
    if(lastDelta < now - 1000000000/120) {
      for {
        player <- state.entities.find(_[ID] == playerID)
      } {
        val inputEvent = Input(keyCode)

        if((player exists isReady) && inputEvent.isDefined) keyCode = null

        val events = if ((player exists notReady) || inputEvent.isDefined) {
          Seq(
            EnemyAI.enemyMoveEvent(player),
            triggerEntityEvents
          ) ++ (inputEvent map (_.apply(player)))
        }
        else Nil

        output.update(state, player)
        state = state.update(events)

        lastDelta = now
      }
    }
  }.start()

  stage.show()
}

