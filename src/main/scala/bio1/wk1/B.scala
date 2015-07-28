package bio1.wk1

import scala.collection.immutable.HashMap
import scala.io.StdIn._

object B {

  def main(args: Array[String]) = {
    val text = readLine
    val k = Integer.parseInt(readLine)
    val count = new Array[Int](text.size)
    var map = new HashMap[String, Int]
    for (i <- 0 to text.length - k) {
      count(i) = 1
      val kmer = text.substring(i, i + k)
      val cnt = map.getOrElse(kmer, 0) + 1
      map = map + (kmer -> cnt)
    }
    val max = map.values.reduceLeft(_ max _)
    map.filterKeys(kmer => map.get(kmer).get == max).keySet.foreach(x => print(x + " "))
  }
}
