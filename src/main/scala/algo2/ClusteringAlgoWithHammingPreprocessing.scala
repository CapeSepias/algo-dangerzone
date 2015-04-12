package algo2

import java.io._
import java.util
import java.util.{Scanner, StringTokenizer}

import scala.collection._
import scala.util.Random

/**
 * Solution for PA#2
 */
object ClusteringAlgoWithHammingPreprocessing {

  var out: PrintWriter = null
  var in: Scanner = null
  var st: StringTokenizer = null

  def main(args: Array[String]): Unit = {
    in = new Scanner(new FileReader("input.txt"))
    out = new PrintWriter(new FileWriter((new File("input2.txt"))))
    solve
    out.close
  }

  def solve: Int = {
    val numberOfNodes = in.nextInt
    val numberOfBits = in.nextInt
    val set = new util.HashSet[String]()
    in.nextLine
    for (i <- 0 until numberOfNodes) {
      set.add(in.nextLine().replace(" ", ""))
    }
    out.print(set.size + " ")
    out.println(numberOfBits)
    val it = set.iterator()
    while (it.hasNext) {
      out.println(it.next)
    }

    return 0
  }
}
