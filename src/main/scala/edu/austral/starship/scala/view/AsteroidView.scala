package edu.austral.starship.scala.view

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.asteroid.Asteroid
import processing.core.{PGraphics, PImage}

case class AsteroidView(asteroid: Asteroid, asteroidImage: PImage) {

  var directionVector: Vector2 = asteroid.directionVector
  var positionVector: Vector2 = asteroid.positionVector
  var destroyed: Boolean = asteroid.destroyed
  var image: PImage = asteroidImage

  def calculateNewPosition(): Unit = {
    positionVector = positionVector + directionVector
  }

  def checkDestroyed(): Unit ={
    destroyed = asteroid.destroyed
  }
}
