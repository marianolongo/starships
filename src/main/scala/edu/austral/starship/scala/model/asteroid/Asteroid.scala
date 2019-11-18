package edu.austral.starship.scala.model.asteroid

import edu.austral.starship.scala.base.collision.CollisionableType
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.CollisionableObject

abstract class Asteroid extends CollisionableObject{


  override val collisionableType: CollisionableType.Value = CollisionableType.Asteroid
  var positionVector: Vector2
  var directionVector: Vector2
  var destroyed: Boolean

  def checkDestroy()
}
