package org.mystic

import java.io.{PrintWriter, FileInputStream, FilenameFilter, File}
import java.util.Scanner

/**
 * Created by mysterion on 05.05.14.
 */
object CleanUpDataPt2 {

  def main(args: Array[String]) = {
    val dir = new File(".")
    val files = dir.listFiles(new FilenameFilter {
      def accept(dir: File, name: String): Boolean = name.endsWith(".txt")
    })
    for (i <- 0 until files.length) {
      val br = new Scanner(new FileInputStream(new File(files(i).getAbsolutePath)), "utf8")
      val out = new PrintWriter("target/" + files(i), "utf8")
      var line = ""
      line = br.nextLine
      var list: List[String] = Nil
      out.println(line)
      do {
        line = br.nextLine
        val splitted = line split " "
        out.println(splitted(0))
        list = splitted(splitted.size - 1) :: list
      } while (br.hasNext)
      list = list.reverse
      for (i <- 0 until list.size) {
        out.println(list(i))
      }
      out.flush
      out.close
    }
  }
}
