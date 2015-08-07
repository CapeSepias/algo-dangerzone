package main.scala.bio1.wk2

import scala.io.StdIn._

object C {

  def main(args: Array[String]) = {
    val pattern = readLine
    val text = readLine
    val d = Integer.parseInt(readLine)
    for (i <- 0 to text.length - pattern.length) {
      var diff = 0
      for (j <- 0 until pattern.length) {
        if (pattern(j) != text(i + j))
          diff += 1
      }
      if (diff <= d) {
        print(i + " ")
      }
    }
  }
}
