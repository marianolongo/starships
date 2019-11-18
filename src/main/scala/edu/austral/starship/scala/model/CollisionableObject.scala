package edu.austral.starship.scala.model

import java.awt.Shape

import edu.austral.starship.scala.base.collision.Collisionable
import edu.austral.starship.scala.base.collision.CollisionableType
import edu.austral.starship.scala.base.vector.Vector2

abstract class CollisionableObject extends Movable with Collisionable[CollisionableObject] {

  val collisionableType: CollisionableType.Value
  var shape: Shape
  var positionVector: Vector2
  var directionVector: Vector2

  def calculateNewPosition()
}
