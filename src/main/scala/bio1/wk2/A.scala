package main.scala.bio1.wk2

import scala.io.StdIn._

object A {

  def main(args: Array[String]) = {
    val genome = readLine
    var countG = 0
    var countC = 0
    val skew = new Array[Int](genome.length + 1)
    var ind = 1
    genome.foreach(x => {
      if (x == 'G')
        countG += 1
      else if (x == 'C')
        countC += 1
      skew(ind) = countG - countC
      ind += 1
    })
    val min = skew.reduceLeft(_ min _)
    for (i <- 0 until skew.length) {
      if (skew(i) == min) {
        print(i + " ")
      }
    }
  }
}
