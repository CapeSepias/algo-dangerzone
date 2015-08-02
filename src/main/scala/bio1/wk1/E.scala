package bio1.wk1

import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.io.StdIn._

object E {

  def main(args: Array[String]) = {
    val genome = readLine
    val ints = readLine.split(" ")
    val k = Integer.parseInt(ints(0))
    val L = Integer.parseInt(ints(1))
    val t = Integer.parseInt(ints(2))
    val ans = new mutable.HashSet[String]
    for (i <- 0 to genome.length - L) {
      val possibleOriC = genome.substring(i, i + L)
      val count = new Array[Int](possibleOriC.size)
      var map = new HashMap[String, Int]
      for (i <- 0 to possibleOriC.length - k) {
        count(i) = 1
        val kmer = possibleOriC.substring(i, i + k)
        val cnt = map.getOrElse(kmer, 0) + 1
        map =  map + (kmer -> cnt)
      }
      val keySet: Set[String] = map.filterKeys(kmer => map.get(kmer).get >= t).keySet
      keySet.foreach(x => ans += x)
    }
    ans.foreach(x => print(x + " "))
  }
}
