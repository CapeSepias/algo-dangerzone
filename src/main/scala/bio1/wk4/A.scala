
package bio1.wk4

import java.util

import scala.StringBuilder
import scala.collection.mutable
import scala.io.StdIn._
import scala.util.Random

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
            pr *= prob(j)(0)
          }
          case 'C' => {
            pr *= prob(j)(1)
          }
          case 'G' => {
            pr *= prob(j)(2)
          }
          case 'T' => {
            pr *= prob(j)(3)
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

  def score(strings: util.ArrayList[String], k: Int): Int = {
    if (strings.isEmpty) {
      return Int.MaxValue
    }
    val matrix = createMatrix(strings, k)
    val sb = new StringBuilder
    for (i <- 0 until matrix.length) {
      var max = -1.0d
      var ind = -1
      for (j <- 0 until 4) {
        if (max < matrix(i)(j)) {
          max = matrix(i)(j)
          ind = j
        }
      }
      ind match {
        case 0 => sb.append('A')
        case 1 => sb.append('C')
        case 2 => sb.append('G')
        case 3 => sb.append('T')
      }
    }
    val con = sb.toString
    var res = 0
    for (i <- 0 until strings.size) {
      for (j <- 0 until strings.get(0).length) {
        if (strings.get(i).charAt(j) != con(j)) {
          res += 1
        }
      }
    }
    res
  }

  def createMatrix(motifs: util.ArrayList[String], k: Int) = {
    val prob = new Array[Array[Double]](k)
    for (i <- 0 until k) {
      prob(i) = new Array[Double](4)
      for (j <- 0 until 4) {
        prob(i)(j) = 1.0d
      }
    }
    for (i <- 0 until motifs.size) {
      for (j <- 0 until motifs.get(i).size) {
        motifs.get(i).charAt(j) match {
          case 'A' => {
            prob(j)(0) += 1
          }
          case 'C' => {
            prob(j)(1) += 1
          }
          case 'G' => {
            prob(j)(2) += 1
          }
          case 'T' => {
            prob(j)(3) += 1
          }
        }
      }
    }
    for (i <- 0 until k) {
      var sum = 0.0
      for (j <- 0 until 4) {
        sum += prob(i)(j)
      }
      for (j <- 0 until 4) {
        if (sum != 0.0f)
          prob(i)(j) /= (sum * 1.0f)
      }
    }
    prob
  }

  def main(args: Array[String]) = {
    val k = readInt
    val t = readInt
    val dnas = new Array[String](t)
    for (i <- 0 until t) {
      dnas(i) = readLine
    }
    var ans = new util.ArrayList[String]
    for (i <- 0 until 10000) {
      val motifs = new util.ArrayList[String]()
      var bestMotifs = new util.ArrayList[String]
      val rand = new Random()
      for (j <- 0 until t) {
        val pos = rand.nextInt(dnas(0).length - k + 1)
        bestMotifs.add(dnas(j).substring(pos, pos + k))
        motifs.add(dnas(j).substring(pos, pos + k))
      }
      var flag = true
      while (flag) {
        val matrix = createMatrix(motifs, k)
        val n = motifs.size
        motifs.clear()
        for (j <- 0 until n) {
          motifs.add(findMostProbableKmer(dnas(j), k, matrix))
        }
        if (score(motifs, k) < score(bestMotifs, k)) {
          bestMotifs = motifs
        } else {
          flag = false
          if (score(bestMotifs, k) < score(ans, k)) {
            ans = bestMotifs
          }
        }
      }
    }
    for (i <- 0 until ans.size) {
      println(ans.get(i))
    }

  }
}
