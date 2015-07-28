package bio1.wk1

import scala.io.StdIn._

object D extends App {
  val pattern = readLine
  val text = readLine
  (0 until text.length).foreach(i => if (text.startsWith(pattern, i)) print(s"$i "))
}