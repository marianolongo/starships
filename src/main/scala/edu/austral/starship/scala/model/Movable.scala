package edu.austral.starship.scala.model

import edu.austral.starship.scala.base.vector.Vector2

trait Movable {

  def accelerate(vector: Vector2)
  def break(vector: Vector2)
  def turn(vector2: Vector2)
}
