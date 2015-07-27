package bio1.wk1

import scala.io.StdIn._

object A {

  def main(args: Array[String]) = {
    val text = readLine
    val pattern = readLine
    var ans = 0
    (0 to text.length - pattern.length).foreach(i => {
      if (text.substring(i, i + pattern.length).equalsIgnoreCase(pattern)) {
        ans += 1
      }
    })
    println(ans)
  }
}
