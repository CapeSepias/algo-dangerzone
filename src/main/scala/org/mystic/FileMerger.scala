package org.mystic

import java.io._
import java.util.{StringTokenizer}

import scala.collection.mutable

object FileMerger {

  var out: PrintWriter = null
  var br: BufferedReader = null
  var st: StringTokenizer = null

  def next: String = {
    while (st == null || !st.hasMoreTokens) {
      val line: String = br.readLine
      if (line == null) {
        return null
      }
      st = new StringTokenizer(line)
    }
    st.nextToken
  }

  class MultiHashSet[T <% Comparable[T]] {
    val map = new mutable.HashMap[T, Int]()

    def count(x: T): Int = {
      return map.getOrElse(x, 0)
    }

    def add(x: T): Unit = add(x, 1)

    def add(x: T, cnt: Int): Unit = map.put(x, count(x) + cnt)

    def remove(x: T): Boolean = {
      val prev = count(x)
      if (prev == 0)
        return false
      if (prev == 1) {
        map.remove(x)
      } else {
        map.put(x, prev - 1)
      }
      return true
    }
  }

  def notNumber(s: String): Boolean = {
    try {
      java.lang.Double.parseDouble(s)
      return false
    } catch {
      case e: Exception =>
    }
    true
  }

  def readFile(s: String) = {
    val map = new MultiHashSet[String]
    br = new BufferedReader(new InputStreamReader(new FileInputStream(s), "UTF8"));
    var cnt = 0L
    var line = next
    while (line != null) {
      cnt += 1
      val lines = line.split("\\=")
      if (lines.size == 2) {
        if (!lines(0).contains("_") && notNumber(lines(0))) {
          map.add(lines(0).toLowerCase, lines(1).toInt)
        }
      }
      line = next
    }
    println(cnt)
    map
  }

  def mergeFiles(a: Map[String, Int], b: Map[String, Int]): Map[String, Int] = {
    val res = new mutable.HashMap[String, Int]()
    (a.keySet ++ b.keySet).foreach(key => res.put(key, a.getOrElse(key, 0) + b.getOrElse(key, 0)))
    res.toMap
  }

  def writeToFile(map: Map[String, Int], name: String): Unit = {
    val out = new PrintWriter(new File(name))
    map.foreach(x => {
      out.println(s"${x._1}=${x._2}")
    })
    out.flush
    out.close
  }

  def mergeOnTheFly(base: MultiHashSet[String], s: String): MultiHashSet[String] = {
    br = new BufferedReader(new InputStreamReader(new FileInputStream(s), "UTF8"));
    var cnt = 0L
    var line = next
    while (line != null) {
      cnt += 1
      val lines = line.split("\\=")
      if (lines.size == 2) {
        if (!lines(0).contains("_") && notNumber(lines(0))) {
          base.add(lines(0).toLowerCase, lines(1).toInt)
        }
      }
      line = next
    }
    println(cnt)
    base
  }

  def main(args: Array[String]) {
    var base = readFile("cleaned-836.txt")
    for (i <- 837 to 859) {
      base = mergeOnTheFly(base, s"cleaned-$i.txt")
    }
    writeToFile(base.map.toMap, "terms.txt")
  }

}
