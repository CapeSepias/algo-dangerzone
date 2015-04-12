package algo2

import java.io._
import java.util
import java.util.StringTokenizer

import scala.collection._

/**
 * Solution for PA#1
 */
object PrimAlgo {

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

  def solve: Int = {
    val numberOfVertexes = nextInt
    val numberOfEdges = nextInt
    val graph = new Array[Array[Int]](numberOfVertexes)
    for (i <- 0 until numberOfVertexes) {
      graph(i) = new Array[Int](numberOfVertexes)
    }
    for (i <- 0 until numberOfVertexes) {
      for (j <- 0 until numberOfVertexes) {
        graph(i)(j) = Int.MaxValue
      }
    }
    for (i <- 0 until numberOfEdges) {
      val from = nextInt - 1
      val to = nextInt - 1
      val cost = nextInt
      graph(from)(to) = cost
      graph(to)(from) = cost
    }
    out.println(slowPrimAlgo(graph))
    return 0
  }

  def slowPrimAlgo(graph: Array[Array[Int]]): Long = {
    val v = new util.HashSet[Int]()
    val x = new util.HashSet[Int]()
    val numberOfVertexes = graph.length
    for (i <- 0 until numberOfVertexes) {
      v.add(i)
    }
    v.remove(0)
    x.add(0)
    var t = 0L
    while (!v.isEmpty) {
      val it = x.iterator()
      var min = Int.MaxValue
      var ind = -1
      var vert = -1
      while (it.hasNext) {
        val vertex = it.next()
        for (i <- 0 until numberOfVertexes) {
          if (min > graph(vertex)(i) && v.contains(i)) {
            min = graph(vertex)(i)
            ind = i
            vert = i
          }
        }
      }
      v.remove(vert)
      x.add(vert)
      t += min
    }
    return t
  }

  // use heap to speed up Prim algo
  def fastPrimAlgo() = ???

}
