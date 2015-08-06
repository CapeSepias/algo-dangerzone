package main.scala.bio1.wk2

import scala.io.StdIn._

object A {

  def main(args: Array[String]) = {
    val genome = readLine
    var countG = 0
    var countC = 0
    print("0 ")
    genome.foreach(x => {
      if (x == 'G')
        countG += 1
      else if (x == 'C')
        countC += 1
      print((countG - countC) + " ")
    })
  }
}
