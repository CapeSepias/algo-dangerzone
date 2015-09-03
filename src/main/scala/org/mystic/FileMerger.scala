package org.mystic

import java.io._
import scala.collection.mutable

object FileMerger {

  def notNumber(s: String): Boolean = {
    try {
      java.lang.Double.parseDouble(s)
      return false
    } catch {
      case e: Exception =>
    }
    true
  }

  def mergeFiles(name1: String, name2: String, dest: String) = {
    val out = new PrintWriter(new File(dest), "UTF8")
    val br1 = new BufferedReader(new InputStreamReader(new FileInputStream(name1), "UTF8"))
    val br2 = new BufferedReader(new InputStreamReader(new FileInputStream(name2), "UTF8"))
    var line1 = br1.readLine()
    var line2 = br2.readLine()
    while (line1 != null && line2 != null) {
      val term1 = line1.split("\\=")(0)
      val term2 = line2.split("\\=")(0)
      val cnt1 = line1.split("\\=")(1).toInt
      val cnt2 = line2.split("\\=")(1).toInt
      if (term1.compareTo(term2) == 0) {
        out.println(s"$term1=${cnt1 + cnt2}")
        line1 = br1.readLine()
        line2 = br2.readLine()
      } else if (term1.compareTo(term2) < 0) {
        out.println(s"$term1=$cnt1")
        line1 = br1.readLine()
      } else {
        out.println(s"$term2=$cnt2")
        line2 = br2.readLine()
      }
    }
    if (line1 == null) {
      while (line2 != null) {
        val term2 = line2.split("\\=")(0)
        val cnt2 = line2.split("\\=")(1).toInt
        out.println(s"$term2=$cnt2")
        line2 = br2.readLine()
      }
    } else if (line2 == null) {
      while (line1 != null) {
        val term1 = line1.split("\\=")(0)
        val cnt1 = line1.split("\\=")(1).toInt
        out.println(s"$term1=$cnt1")
        line1 = br1.readLine()
      }
    }
    out.flush()
    out.close()
  }

  def cleanFile(fileName: String, destName: String) = {
    val out = new PrintWriter(new File(destName), "UTF8")
    val br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"))
    var line1 = br.readLine()
    while (line1 != null) {
      if (!line1.contains('_') && notNumber(line1.split("\\=")(0))) {
        out.println(line1)
      }
      line1 = br.readLine()
    }
    out.flush()
    out.close()
  }


  def compressFile(filename: String, destName: String) = {
    val out = new PrintWriter(new File(destName), "UTF8")
    val br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"))
    var line1 = br.readLine()
    val map = new mutable.HashMap[String, Int]()
    while (line1 != null) {
      val s = line1.split("\\=")
      map.put(s(0).toLowerCase, Integer.parseInt(s(1)))
      line1 = br.readLine()
    }
    map.keySet.toList.sorted.foreach(str => {
      out.println(s"$str=${map.get(str).get}")
    })
    out.flush()
    out.close()

  }

  def main(args: Array[String]) {
    for (i <- 836 to 859) {
      cleanFile(s"D:\\\\terms-$i.txt", s"D:\\\\cleaned-$i.txt")
    }
    for (i <- 836 to 859) {
      compressFile(s"D:\\\\cleaned-$i.txt", s"D:\\\\compressed-$i.txt")
    }
    for (i <- 836 to 859 by 2) {
      mergeFiles(s"D:\\\\compressed-$i.txt", s"D:\\\\compressed-${i + 1}.txt", s"D:\\\\merged-$i.txt")
    }
    for (i <- 836 to 859 by 4) {
      mergeFiles(s"D:\\\\merged-$i.txt", s"D:\\\\merged-${i + 2}.txt", s"D:\\\\merged2-$i.txt")
    }
    for (i <- 836 to 859 by 8) {
      mergeFiles(s"D:\\\\merged2-$i.txt", s"D:\\\\merged2-${i + 4}.txt", s"D:\\\\merged3-$i.txt")
    }
    mergeFiles(s"D:\\\\merged3-836.txt", s"D:\\\\merged3-844.txt", s"D:\\\\merged-4-836.txt")
    mergeFiles(s"D:\\\\merged-4-836.txt", s"D:\\\\merged3-852.txt", s"D:\\\\final-dump.txt")
  }

}
