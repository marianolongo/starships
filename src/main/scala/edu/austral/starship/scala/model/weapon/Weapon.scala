package edu.austral.starship.scala.model.weapon

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.bullet.Bullet
import edu.austral.starship.scala.model.starship.Starship

abstract class Weapon {

  var bullets: List[Bullet]
  val starship: Starship
  var coolingTime: Int = 0
  def fire(direction: Vector2, position: Vector2): Boolean

  def addBullet(bullet: Bullet): Unit ={
    bullets = bullet :: bullets
  }
  def increaseCooling(): Unit = coolingTime = coolingTime + 1

  def coolingReturn(): Unit = coolingTime = 0
}
