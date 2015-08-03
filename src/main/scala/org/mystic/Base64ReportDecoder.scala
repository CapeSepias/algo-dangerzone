package org.mystic

import com.github.marklister.base64.Base64._
import java.io._
import java.util.StringTokenizer

object Base64ReportDecoder  {
  
  var out: PrintWriter = null
  var br: BufferedReader = null
  var st: StringTokenizer = null

  def main(args: Array[String]): Unit = {
    br = new BufferedReader(new InputStreamReader(new FileInputStream("report.txt"), "UTF8"));
    out = new PrintWriter(new BufferedOutputStream(System.out))
    solve
    out.close
  }

  def next: String = {
    while (st == null || !st.hasMoreTokens) {
      st = new StringTokenizer(br.readLine)
    }
    return st.nextToken
  }

  def nextInt: Int = return Integer.parseInt(next)

  def nextLong: Long = return java.lang.Long.parseLong(next)

  def nextDouble: Double = return java.lang.Double.parseDouble(next)
  
 def removeNoise(s: String): String = {
    new String(Decoder(s).toByteArray)
  }

  def solve: Int = {
    val n = nextInt
    for (i <- 0 until n) {
      val testId = next
      val userId = next
      val name = next
      val email = next
      val age = next
      val lang = next
      val gender = next
      var report = ""
      try {
        report = removeNoise(next).substring(12)
      } catch {
        case e: Exception => println(s"Problem with userId ${userId}")
      }
      if (testId.equalsIgnoreCase("1") && !report.isEmpty) {
        try {
          val writer = new PrintWriter(s"$name-$email-$age-$lang-$gender.csv")
          report.substring(0, report.length - 2).split("\\),").foreach(x => {
            x.trim.substring(1).split("\\,").foreach(x => writer.print(x + " "))
            writer.println
          })
          writer.flush
          writer.close
        } catch {
          case io: IOException => println(s"Error with processing report with userId = ${userId}")
        }
      }
    }
    return 0
  }
}
