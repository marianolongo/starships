package edu.austral.starship.scala.model.asteroid

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.CollisionableObject

abstract class Asteroid extends CollisionableObject{

  var positionVector: Vector2
  var directionVector: Vector2
  var destroyed: Boolean

  def checkDestroy()
}
