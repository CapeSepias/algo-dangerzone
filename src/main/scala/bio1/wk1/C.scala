package bio1.wk1

import scala.io.StdIn._

object C extends App {
  val text = readLine
  println(text.reverse.foldLeft(new StringBuilder)((compliment, ch) => {
    ch match {
      case 'A' => compliment += 'T'
      case 'G' => compliment += 'C'
      case 'T' => compliment += 'A'
      case 'C' => compliment += 'G'
    }
  }))
}