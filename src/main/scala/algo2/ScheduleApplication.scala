import java.util

import scala.collection._

import java.io._
import java.util.{Random, StringTokenizer}

/**
 * Solution for PA#1
 */
object ScheduleApplication {

  var out: PrintWriter = null
  var br: BufferedReader = null
  var st: StringTokenizer = null

  def main(args: Array[String]): Unit = {
    br = new BufferedReader(new FileReader("input.txt"))
    //    br = new BufferedReader(new InputStreamReader(System.in))
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

  class MultiHashSet[T <% Comparable[T]] {
    val map = new mutable.HashMap[T, Int]()

    def count(x: T): Int = {
      return map.getOrElse(x, 0)
    }

    def add(x: T): Unit = map.put(x, count(x) + 1)

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

  class MultiTreeSet[T <% Comparable[T]] {
    val map = new util.TreeMap[T, Int]()

    def count(x: T): Int = {
      val res = map.get(x)
      if (res == null)
        return 0
      return res
    }

    def add(x: T): Unit = map.put(x, count(x) + 1)

    def first(): T = return map.firstKey()

    def last(): T = return map.lastKey()

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

  case class Job1(val weight: Int, val length: Int) extends Comparable[Job1] {
    override def compareTo(o: Job1): Int = {
      if (weight - length == o.weight - o.length) {
        return -weight.compareTo(o.weight)
      }
      return -(weight - length).compareTo(o.weight - o.length)
    }
  }

  case class Job2(val weight: Int, val length: Int) extends Comparable[Job2] {
    override def compareTo(o: Job2): Int = {
      return -(weight.toDouble / length.toDouble).compareTo(o.weight.toDouble / o.length.toDouble)
    }
  }

  def solve: Int = {
    val n = nextInt
    val jobs = new Array[Job2](n)
    for (i <- 0 until n) {
      jobs(i) = new Job2(nextInt, nextInt)
    }
    var completionTime = 0L
    var ans = 0L
    val sortedJobs = jobs.sorted
    for (i <- 0 until n) {
      completionTime += sortedJobs(i).length
      ans += completionTime * sortedJobs(i).weight
    }
    out.println(ans)
    //    jobs.sorted.foreach(j => out.println(j.weight + " " + j.length))
    return 0
  }

}
