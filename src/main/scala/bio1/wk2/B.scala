package main.scala.bio1.wk2

import scala.io.StdIn._

object B {

  def main(args: Array[String]) = {
    val str1 = readLine
    val str2 = readLine
    var ans = 0
    for (i <- 0 until str1.length) {
      if (str1(i) != str2(i))
        ans += 1
    }
    println(ans)
  }
}
