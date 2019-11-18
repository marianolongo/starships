package edu.austral.starship.scala.view

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.starship.Starship
import processing.core.PImage

case class StarshipView(starship: Starship, img: PImage) {

  var positionVector: Vector2 = starship.positionVector
  var directionVector: Vector2 = starship.directionVector
  var image: PImage = img

  def setImg(img: PImage): Unit ={
    image = img
  }

  def setPositionVector(vector2: Vector2): Unit ={
    positionVector = vector2
  }

  def setDirectionVector(vector2: Vector2): Unit ={
    directionVector = vector2
  }

}
