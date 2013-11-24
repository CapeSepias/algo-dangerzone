package org.mystic

import java.util.Scanner
import java.io._


/**
 * @author kperikov
 *         Clean up data for previous version of Kagan test
 */
object CleanUpData {

  def main(args: Array[String]) = {
    val dir = new File(".")
    val files = dir.listFiles(new FilenameFilter {
      def accept(dir: File, name: String): Boolean = name.endsWith(".txt")
    })
    for (i <- 0 until files.length) {
      val br = new Scanner(new FileInputStream(new File(files(i).getAbsolutePath)), "utf8")
      val out = new PrintWriter("out/" + files(i), "utf8")
      var line = ""
      do {
        line = br.nextLine
        if (line matches ".*\\d+.*") {
          val splitted = line split " "
          for (i <- 0 until splitted.length) {
            if (splitted(i) matches ".*\\d+.*") {
              out.print(splitted(i).replace(';', ' ') + " ")
            }
          }
          out.println
        } else {
          out.println(line)
        }
      } while (br.hasNext)
      out.flush
      out.close
    }
  }

}
