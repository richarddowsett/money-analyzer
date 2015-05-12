package controllers

import play.api._
import play.api.mvc._
import utils.Conversions._

object Application extends Controller {

  def index = Action {

    val one = "1".increment
    val oneString = "one".increment
    val two = "2".increment

    Ok(views.html.index(
      s"""
         | Using exceptions
         | value of 1: $one
         | value of one: $oneString
         | value of twoString: ${"two".increment}
         | value of two: $two
       """.stripMargin))
  }

}