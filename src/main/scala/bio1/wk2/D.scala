package main.scala.bio1.wk2

import scala.io.StdIn._

object D {

  def equalsWithDiff(s1: String, s2: String, d: Int): Boolean = {
    var diff = 0
    for (j <- 0 until s1.length) {
      if (s1(j) != s2(j))
        diff += 1
    }
    if (diff <= d) true else false
  }

  def main(args: Array[String]) = {
    val pattern = readLine
    val text = readLine
    val d = Integer.parseInt(readLine)
    var ans = 0
    (0 to text.length - pattern.length).foreach(i => {
      if (equalsWithDiff(text.substring(i, i + pattern.length), pattern, d)) {
        ans += 1
      }
    })
    println(ans)
  }
}
