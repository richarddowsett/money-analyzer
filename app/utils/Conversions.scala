package utils

import scala.util.{Failure, Try}

/**
 * Created by Richard on 12/05/2015.
 */
object Conversions {

  implicit class IntExt(s: String) {

    val regex = "\\d*"

    def increment: Int = {
      val attempt = Try(s.toInt + 1)
      val lastDitch = Try(s match {
        case "one" => 1
        case x => throw new NullPointerException
      })
      attempt orElse lastDitch getOrElse 0
    }

  }

}
