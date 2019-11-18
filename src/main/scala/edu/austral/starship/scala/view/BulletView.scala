package edu.austral.starship.scala.view

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.bullet.Bullet
import processing.core.PImage

case class BulletView(bul: Bullet, bulletImage: PImage) {

  var directionVector: Vector2 = bul.directionVector
  var positionVector: Vector2 = bul.positionVector
  var image: PImage = bulletImage
  var destroyed: Boolean = bul.destroyed

  def calculateNewPosition(): Unit = {
    positionVector = positionVector + directionVector
  }

  def checkDestroyed(): Unit ={
    destroyed = bul.destroyed
  }
}
