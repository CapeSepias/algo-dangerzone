package org.mystic

import java.io.{PrintWriter, File}
import java.util.Scanner

import scala.collection.mutable

object FileMerger {

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

  def readFile(s: String) = {
    val map = new MultiHashSet[String]
    val in = new Scanner(new File(s))
    while (in.hasNextLine()) {
      val line = in.nextLine().split("\\=")
      if (line.size == 2) {
        map.add(line(0), line(1).toInt)
      }
    }
    map
  }

  def mergeFiles(a: MultiHashSet[String], b: MultiHashSet[String]): Map[String, Int] = {
    val map1 = a.map
    val map2 = b.map
    val res = (map1.keySet ++ map2.keySet).map(i => (i, map1.getOrElse(i, 0) + map2.getOrElse(i, 0))).toMap
    res
  }

  def writeToFile(map: Map[String, Int]): Unit = {
    val out = new PrintWriter(new File("result.txt"))
    map.foreach(x => {
      out.println(s"${x._1}=${x._2}")
    })
    out.flush
    out.close
  }

  def main(args: Array[String]) {
    val a = readFile(args(0))
    val b = readFile(args(1))
    val c = mergeFiles(a, b)
    writeToFile(c)
  }

}
