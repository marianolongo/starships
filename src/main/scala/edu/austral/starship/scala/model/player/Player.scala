package edu.austral.starship.scala.model.player

case class Player(poi: Long, username: String) {

  var points: Long = poi
  var lives: Int = 3
  val name: String = username
/*  var keyMap: Map[Int, Actions.Value] = Map((keys.head, Actions.Accelerate),
    (keys(1), Actions.Turn),
    (keys(2), Actions.Break),
    (keys(3), Actions.Fire))*/
  def addPoints(p: Long): Unit = points = points + p

  def takeLife(): Unit = {
    if(lives != 0) lives = lives - 1
  }
}
