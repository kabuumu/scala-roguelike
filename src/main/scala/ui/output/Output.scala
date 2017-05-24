package ui.output

import core.entity.Entity
import core.system.GameState
import roguelike.combat.Health
import roguelike.experience.{Experience, Level}

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color
import scalafx.scene.text.Font

/**
  * Created by rob on 13/04/16.
  */
class Output(canvas: Canvas) {
  val g2d = canvas.getGraphicsContext2D
  lazy val gameArea = new GameArea(canvas)

  def update(state: GameState, player: Entity): Unit = {
    g2d.clearRect(0, 0, canvas.getWidth, canvas.getHeight)
    g2d.setFill(Color.Black)
    g2d.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    gameArea.update(state, player)
    drawStats(player)
  }

  def drawStats(player: Entity) =
    for {
      Health(currentHealth, maxHealth) <- player[Health]
      Level(level) <- player[Level]
      Experience(currentExp, maxExp) <- player[Experience]
    } {
      g2d.setFill(Color.Green)
      g2d.setFont(Font.font(16))

      g2d.fillText(s"Health: $currentHealth/$maxHealth", gameArea.width, 20)
      g2d.fillText(s"Level: $level", gameArea.width, 40)
      g2d.fillText(s"XP: $currentExp/$maxExp", gameArea.width, 60)
    }
}
