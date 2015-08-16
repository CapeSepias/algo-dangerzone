package bio1.wk3

import scala.collection.mutable
import scala.io.StdIn._

object A {

  def equalsWithDiff(s1: String, s2: String, d: Int): Boolean = {
    var diff = 0
    if (s1.length != s2.length)
      return false
    for (j <- 0 until s1.length) {
      if (s1(j) != s2(j))
        diff += 1
    }
    if (diff <= d) true else false
  }

  def containsWithDiff(s1: String, s2: String, d: Int): Boolean = {
    for (i <- 0 to s1.length - s2.length) {
      if (equalsWithDiff(s1.substring(i, i + s2.length), s2, d)) {
        return true
      }
    }
    false
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
    val k = readInt
    val d = readInt
    val n = readInt
    val dnas = new Array[String](n)
    for (i <- 0 until n) {
      dnas(i) = readLine
    }
    val possiblePatterns = new mutable.HashSet[String]()
    generate4Kmers(k).foreach(kmer => {
      for (i <- 0 until n) {
        val s = dnas(i)
        if (containsWithDiff(s, kmer, d))
          possiblePatterns += kmer
      }
    })
    possiblePatterns.filter(x => dnas.forall(y => containsWithDiff(y, x, d))).foreach(x => print(x + " "))
  }
}
