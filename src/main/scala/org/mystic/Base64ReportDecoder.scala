package org.mystic

import java.io.PrintWriter
import java.util
import java.util.Scanner
import scala.collection.immutable.TreeMap
import com.github.marklister.base64.Base64._
import java.io._
import java.util.StringTokenizer
import scala.collection.mutable

object Base64ReportDecoder {

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
        report = removeNoise(next)
      } catch {
        case e: Exception => println(s"Problem with userId ${userId}")
      }
      testId match {
        case "1" => {
          // ultra rapid
          try {
            report = report.substring(12)
            val writer = new PrintWriter(s"UltraRapid-$name-$email-$age-$lang-$gender.csv")
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
        case "2" => {
          // kagan
          try {
            val writer = new PrintWriter(s"Kagan-$name-$email-$age-$lang-$gender.csv")
            val splitted = report.split("\\|")
            var prevTime = 0L
            val correctAnswers = mutable.HashMap[Int, Int]()
            correctAnswers.put(1, 1)
            correctAnswers.put(2, 5)
            correctAnswers.put(3, 4)
            correctAnswers.put(4, 7)
            correctAnswers.put(5, 1)
            correctAnswers.put(6, 8)
            correctAnswers.put(7, 4)
            correctAnswers.put(8, 1)
            correctAnswers.put(9, 8)
            correctAnswers.put(10, 4)
            correctAnswers.put(11, 5)
            correctAnswers.put(12, 1)
            correctAnswers.put(13, 5)
            correctAnswers.put(14, 5)
            for (i <- 0 until splitted.length by 3) {
              val ans = splitted(i).toInt
              val round = splitted(i + 1).toInt
              val time = splitted(i + 2).toLong
              val correctAnswer = correctAnswers.get(round)
              if (ans == correctAnswer.get)
                writer.println(s"2 ${time - prevTime}")
              else
                writer.println(s"1 ${time - prevTime}")
              prevTime = time
            }
            writer.flush
            writer.close
          } catch {
            case io: IOException => println(s"Error with processing report with userId = ${userId}")
          }
        }
        case "3" => {
          // gotshild
          try {
            val writer = new PrintWriter(s"Gothshild-$name-$email-$age-$lang-$gender.csv")
            val splitted = report.split("\\|")
            val correctAnswers = mutable.HashMap[Int, String]()
            correctAnswers.put(1, "A")
            correctAnswers.put(2, "B")
            correctAnswers.put(3, "V")
            correctAnswers.put(4, "G")
            correctAnswers.put(5, "V")
            correctAnswers.put(6, "V")
            correctAnswers.put(7, "A")
            correctAnswers.put(8, "V")
            correctAnswers.put(9, "D")
            correctAnswers.put(10, "D")
            correctAnswers.put(11, "B")
            correctAnswers.put(12, "A")
            correctAnswers.put(13, "A")
            correctAnswers.put(14, "V")
            correctAnswers.put(15, "B")
            correctAnswers.put(16, "D")
            correctAnswers.put(17, "A")
            correctAnswers.put(18, "D")
            correctAnswers.put(19, "B")
            correctAnswers.put(20, "V")
            correctAnswers.put(21, "G")
            correctAnswers.put(22, "B")
            correctAnswers.put(23, "G")
            correctAnswers.put(24, "A")
            correctAnswers.put(25, "D")
            correctAnswers.put(26, "B")
            correctAnswers.put(27, "A")
            correctAnswers.put(28, "D")
            correctAnswers.put(29, "V")
            correctAnswers.put(30, "B")
            for (i <- 0 until splitted.length by 3) {
              val ans = splitted(i + 1)
              val round = splitted(i).toInt
              val time = splitted(i + 2).toLong
              val correctAnswer = correctAnswers.get(round)
              if (ans == correctAnswer.get)
                writer.println(s"2 ${time}")
              else
                writer.println(s"1 ${time}")
            }
            writer.flush
            writer.close
          } catch {
            case io: IOException => println(s"Error with processing report with userId = ${userId}")
          }
        }
        case "4" => {
          // stroop
          try {
            val writer = new PrintWriter(s"Stroop-$name-$email-$age-$lang-$gender.csv")
            val splitted = report.split("\\|")
            for (i <- 0 until splitted.length by 2) {
              writer.println(s"${splitted(i)} ${splitted(i + 1)}")
            }
            writer.flush
            writer.close
          } catch {
            case io: IOException => println(s"Error with processing report with userId = ${userId}")
          }
        }
      }
      if (testId.equalsIgnoreCase("1") && !report.isEmpty) {

      }
    }
    return 0
  }
}
