package org.mystic

import java.io._
import java.util.{StringTokenizer}

import org.mapdb.{HTreeMap, Serializer, DBMaker}

import scala.collection.mutable

object FileMerger {

  var out: PrintWriter = null
  var br: BufferedReader = null
  var st: StringTokenizer = null
  var map: HTreeMap[String, Int] = null

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

  class MultiHashSet {

    def count(x: String): Int = {
      if (!map.containsKey(x)) 0 else map.get(x)
    }

    def add(x: String): Unit = add(x, 1)

    def add(x: String, cnt: Int): Unit = map.put(x, count(x) + cnt)

    def remove(x: String): Boolean = {
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
    val map = new MultiHashSet
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

  def writeToFile(map: HTreeMap[String, Int], name: String): Unit = {
    val out = new PrintWriter(new File(name))
    val it = map.entrySet().iterator()
    while (it.hasNext) {
      val next = it.next()
      out.println(s"${next.getKey}=${next.getValue}")
    }
    out.flush
    out.close
  }

  def mergeOnTheFly(base: MultiHashSet, s: String): MultiHashSet = {
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
    val db = DBMaker
      .fileDB(new File("D:\\\\mapdb"))
      .fileLockDisable
      .asyncWriteEnable
      .allocateStartSize(20 * 1024 * 1024 * 1024)
      .allocateIncrement(1024 * 1024 * 512)
      .cacheHashTableEnable()
      .cacheSize(1000000)
      .make()
    map = db.hashMapCreate("terms")
      .keySerializer(Serializer.STRING)
      .valueSerializer(Serializer.INTEGER)
      .makeOrGet()
    var base = readFile("cleaned-836.txt")
    for (i <- 837 to 859) {
      base = mergeOnTheFly(base, s"cleaned-$i.txt")
      db.commit()
      db.compact()
    }
    writeToFile(map, "terms.txt")
    db.close()
  }

}
