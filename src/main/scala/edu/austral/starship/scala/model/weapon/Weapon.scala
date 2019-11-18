package edu.austral.starship.scala.model.weapon

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.bullet.Bullet
import edu.austral.starship.scala.model.starship.Starship

abstract class Weapon {

  var bullets: List[Bullet]
  val starship: Starship
  def fire(direction: Vector2, position: Vector2)
}
