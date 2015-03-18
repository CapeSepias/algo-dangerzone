package algo1

import java.io.{BufferedReader, InputStreamReader}
import java.util.StringTokenizer

import scala.math._

object ShortestPaths {

  var br: BufferedReader = null
  var st: StringTokenizer = null

  def nextInt: Int = {
    Integer.parseInt(next)
  }

  def next: String = {
    while (st == null || !st.hasMoreTokens) {
      st = new StringTokenizer(br.readLine)
    }
    st.nextToken
  }

  def nextLong: Int = {
    Integer.parseInt(next)
  }

  def solveDijkstraSlow(): Unit = {

  }

  /**
   * Dijkstra algorithm, with fast data structure (heap). Use Scala standard library
   */
  def solveDijkstraStandardHeap(): Unit = {

  }

  /**
   * Dijkstra algorithm, with fast data structure (heap). Write own heap implementation
   */
  def solveDijkstraSelfWrittenHeap(): Unit = {

  }

  def main(args: Array[String]) = {
    br = new BufferedReader(new InputStreamReader(System.in))
    val n = readLine.trim.toInt
    val m = readLine.trim.toInt
    val graph = new Array[Array[Long]](n)
    for (i <- 0 until n) {
      graph(i) = new Array[Long](n)
      for (j <- 0 until n) {
        graph(i)(j) = Long.MaxValue
      }
    }
    for (i <- 0 until m) {
      val arr = readInts
      for (j <- 1 to arr.length / 2) {
        graph(arr(0) - 1)(arr(j * 2 - 1) - 1) = arr(j * 2)
      }
    }
    val sol = solveFloydWarshall(graph)
    // 7,37,59,82,99,115,133,165,188,197.
    print(sol(0)(6) + ",")
    print(sol(0)(36) + ",")
    print(sol(0)(58) + ",")
    print(sol(0)(81) + ",")
    print(sol(0)(98) + ",")
    print(sol(0)(114) + ",")
    print(sol(0)(132) + ",")
    print(sol(0)(164) + ",")
    print(sol(0)(187) + ",")
    print(sol(0)(196) + ",")
  }

  def solveFloydWarshall(graph: Array[Array[Long]]): Array[Array[Long]] = {
    val n = graph.length
    val result = new Array[Array[Long]](n)
    for (i <- 0 until n) {
      result(i) = new Array[Long](n)
    }
    for (i <- 0 until n) {
      for (j <- 0 until n) {
        result(i)(j) = graph(i)(j)
      }
    }
    for (k <- 0 until n) {
      for (i <- 0 until n) {
        for (j <- 0 until n) {
          if (result(i)(k) < Long.MaxValue && result(k)(j) < Long.MaxValue) {
            result(i)(j) = min(result(i)(j), result(i)(k) + result(k)(j))
          }
        }
      }
    }
    result
  }

  def readInts = readString.trim.split(" ").map(_.toInt)

  def readString = Console.readLine
}
