package algo2

import java.io._
import java.util
import java.util.StringTokenizer

import scala.collection._

/**
 * Solution for PA#2
 */
object KraskalAlgo {

  var out: PrintWriter = null
  var br: BufferedReader = null
  var st: StringTokenizer = null

  def main(args: Array[String]): Unit = {
    br = new BufferedReader(new FileReader("edges.txt"))
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

  case class Edge(from: Int, to: Int, cost: Int) extends Comparable[Edge] {
    override def compareTo(o: Edge): Int = Integer.compare(cost, o.cost)
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
    val edges = new Array[Edge](numberOfEdges)
    for (i <- 0 until numberOfEdges) {
      val from = nextInt - 1
      val to = nextInt - 1
      val cost = nextInt
      graph(from)(to) = cost
      graph(to)(from) = cost
    }
    out.println(kraskalSlowAlgo(graph, edges))
    return 0
  }

  def kraskalSlowAlgo(graph: Array[Array[Int]], edges: Array[Edge]): Long = {
    val sortedEdges = edges.sorted
    for (i <- 0 until sortedEdges.length) {
      val edge = sortedEdges(i)
      val u = edge.from
      val v = edge.to
      // todo check edge(i)
      // try to add it to MST
      // check if there is no path in MST from u to v, then add it to MST
      // we could do bfs or dfs here
    }
    // O(m * n) time
    return 0L
  }


  // union find structure
  def kraskalFastAlgo(graph: Array[Array[Int]], edges: Array[Edge]): Long = {
    val sortedEdges = edges.sorted
    val unionFind: UnionFind = new ClassicalUnionFind
    for (i <- 0 until sortedEdges.length) {
      val edge = sortedEdges(i)
      val u = edge.from
      val v = edge.to
      // todo check edge(i) with union find structure

    }
    // O(m * log m) time
    return 0L
  }


  // TODO scala style javadoc?
  trait UnionFind {

    case class Node(name: Int)

    /**
     * by name of object (int for simplicity) get a name(int for simplicity) of group it includes
     */
    def find(x: Int): Int

    /**
    merge two group with name g1 and g2 (int for simplicity) in one group
      */
    def union(g1: Int, g2: Int)

  }

  case class Node

  class ClassicalUnionFind extends UnionFind {
    override def find(x: Int): Int = ???

    override def union(g1: Int, g2: Int): Unit = ???
  }

}
