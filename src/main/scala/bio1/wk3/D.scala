package bio1.wk3

import java.util

import scala.collection.mutable
import scala.io.StdIn._

object D {

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

  def calcD(dnas: Array[String], kmer: String) = {
    var sum = 0
    for (i <- 0 until dnas.length) {
      var min = Int.MaxValue
      for (j <- 0 to dnas(i).length - kmer.length) {
        var cnt = 0
        for (k <- 0 until kmer.length) {
          if (kmer(k) != dnas(i)(k + j))
            cnt += 1
        }
        min = Math.min(min, cnt)
      }
      sum += min
    }
    sum
  }

  def findMostProbableKmer(s: String, k: Int, prob: Array[Array[Double]]) = {
    var max = Double.MinValue
    var bestKmer = ""
    for (i <- 0 to s.length - k) {
      var pr = 1.0d
      for (j <- 0 until k) {
        val ch = s(i + j)
        ch match {
          case 'A' => {
            pr *= prob(0)(j)
          }
          case 'C' => {
            pr *= prob(1)(j)
          }
          case 'G' => {
            pr *= prob(2)(j)
          }
          case 'T' => {
            pr *= prob(3)(j)
          }
        }
      }
      if (pr > max) {
        max = pr
        bestKmer = s.substring(i, i + k)
      }
    }
    bestKmer
  }

  def score(strings: util.ArrayList[String]) = {
    
  }

  def main(args: Array[String]) = {
    val k = readInt
    val t = readInt
    val dnas = new Array[String](t)
    for (i <- 0 until t) {
      dnas(i) = readLine
    }
    var bestMotifs = new util.ArrayList[String]
    for (i <- 0 until t) {
      bestMotifs.add(dnas(i).substring(0, k))
    }
    val motifs = new util.ArrayList[String]()
    for (i <- 0 to dnas(0).length - k) {
      val kmer = dnas(0).substring(i, i + k)
      motifs.add(kmer)
      for (j <- 1 until dnas.length) {
        val matrix = createMatrix(motifs)
        motifs.add(findMostProbableKmer(dnas(j), k, matrix))
      }
      if (score(motifs) > score(bestMotifs)) {
        bestMotifs = motifs
      }
    }
    for (i <- 0 until bestMotifs.size) {
      println(bestMotifs.get(i))
    }

  }
}
