package org.mystic
import java.io._

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
    val out = new PrintWriter(new File(dest))
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
      while(line2 != null) {
        val term2 = line2.split("\\=")(0)
        val cnt2 = line2.split("\\=")(1).toInt
        out.println(s"$term2=$cnt2")
        line2 = br2.readLine()
      }
    } else if (line2 == null) {
      while(line1 != null) {
        val term1 = line1.split("\\=")(0)
        val cnt1 = line1.split("\\=")(1).toInt
        out.println(s"$term1=$cnt1")
        line1 = br1.readLine()
      }
    }
    out.flush()
    out.close()
  }


  def main(args: Array[String]) {
    for (i <- 836 to 859 by 2) {
      mergeFiles(s"cleaned-$i.txt", s"cleaned-${i + 1}.txt", s"merged-$i.txt")
    }
  }

}
