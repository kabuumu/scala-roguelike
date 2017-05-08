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
import refactor.roguelike.actors.EnemyMarker
import refactor.roguelike.ai.Enemy
import refactor.roguelike.async.{Async, Initiative}
import refactor.roguelike.map.MapConverter
import refactor.roguelike.map.MapConverter._
import refactor.roguelike.movement.Direction._
import refactor.roguelike.movement.Movement._
import refactor.roguelike.movement._

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
  val enemyID = new ID

  val startingPlayer = Entity(playerID, Position(1, 1), Facing(Up))
  val startingEnemy = Entity(new ID, EnemyMarker, Position(20, 5), Facing(Left), Initiative(max = 25))
  val walls = convert(tileMap)

  var state: GameState = GameState(Seq(
    startingPlayer,
    startingEnemy
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

      val enemyMoveEvent = for {
        player <- state.entities.find(_[ID] contains playerID)
      } yield Enemy.enemyMoveEvent(player)

      state = state.update(
        Seq(velocityUpdate,
          Async.updateInitiative
        ) ++ inputEvent ++ enemyMoveEvent
      )

      new Output(state, canvas).update()
      keyCode = null
      lastDelta = now
    }
  }.start()

  stage.show()
}

