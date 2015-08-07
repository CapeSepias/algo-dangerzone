package main.scala.bio1.wk2

import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.io.StdIn._

object E {

  def equalsWithDiff(s1: String, s2: String, d: Int): Boolean = {
    var diff = 0
    for (j <- 0 until s1.length) {
      if (s1(j) != s2(j))
        diff += 1
    }
    if (diff <= d) true else false
  }

  def addTrailingZero(s: String, len: Int) = {
    val sb = new StringBuilder
    for (i <- 0 until len - s.length) {
      sb.append("0")
    }
    sb.append(s).toString
  }

  def transformToACGT(s: String) = {
    for {
      ch <- s
    } yield ch match {
      case '0' => 'A'
      case '1' => 'C'
      case '2' => 'G'
      case '3' => 'T'
    }
  }

  def generate4Kmers(k: Int) = {
    val set = new mutable.HashSet[String]()
    for (i <- 0 to Math.pow(4, k).toInt) {
      val s = transformToACGT(addTrailingZero(Integer.toString(i, 4), k))
      set += s
    }
    set
  }

  def main(args: Array[String]) = {
    val text = readLine
    val k = Integer.parseInt(readLine)
    val d = Integer.parseInt(readLine)
    var ans = 0
    val set = generate4Kmers(k)
    var map = new HashMap[String, Int]
    set.foreach(pattern => {
      var count = 0
      (0 to text.length - pattern.length).foreach(i => {
        if (equalsWithDiff(text.substring(i, i + pattern.length), pattern, d)) {
          count += 1
        }
      })
      map = map + (pattern -> count)
    })
    val max = map.values.reduceLeft(_ max _)
    map.filterKeys(x => {
      map.get(x).get == max
    }).foreach(x => print(x._1 + " "))
  }
}
