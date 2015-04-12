package algo2

import java.io._
import java.util
import java.util.StringTokenizer

import scala.collection._
import scala.util.Random

/**
 * Solution for PA#2
 */
object ClusteringAlgo {

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

  case class Edge(from: Int, to: Int, cost: Int) extends Comparable[Edge] {
    override def compareTo(o: Edge): Int = Integer.compare(cost, o.cost)
  }

  def solve: Int = {
    val numberOfNodes = nextInt
    val graph = new Array[Array[Int]](numberOfNodes)
    for (i <- 0 until numberOfNodes) {
      graph(i) = new Array[Int](numberOfNodes)
    }
    for (i <- 0 until graph.length) {
      for (j <- 0 until graph.length) {
        graph(i)(j) = Int.MaxValue
      }
    }
    val numberOfEdges = (numberOfNodes * (numberOfNodes - 1)) / 2
    val edges = new Array[Edge](numberOfEdges)
    for (i <- 0 until numberOfEdges) {
      val from = nextInt - 1
      val to = nextInt - 1
      val cost = nextInt
      edges(i) = new Edge(from, to, cost)
      graph(from)(to) = cost
      graph(to)(from) = cost
    }
    out.println(clusterSlowAlgo(graph, edges, 4))
    return 0
  }

  def clusterSlowAlgo(graph: Array[Array[Int]], edges: Array[Edge], desiredNumberOfClusters: Int): Long = {
    val sortedEdges = edges.sorted
    var clusters = graph.length
    var ind = 0
    val uf: UnionFind = new ClassicalUnionFind(clusters)
    while (clusters > desiredNumberOfClusters) {
      val edge = sortedEdges(ind)
      if (uf.find(edge.from) != uf.find(edge.to)) {
        uf.union(edge.from, edge.to)
        // merge cluster
        clusters -= 1
      }
      ind += 1
    }
    while (clusters != desiredNumberOfClusters -1) {
      val edge = sortedEdges(ind)
      if (uf.find(edge.from) != uf.find(edge.to)) {
        return edge.cost
      }
      ind += 1
    }
    return 0
  }


  // TODO scala style javadoc?
  trait UnionFind {

    case class Node(name: Int) {
      var leader: Node = this
    }

    /**
     * by name of object (int for simplicity) get a name(int for simplicity) of group it includes
     */
    def find(x: Int): Int

    /**
    merge two group with name g1 and g2 (int for simplicity) in one group
      */
    def union(g1: Int, g2: Int)

  }

  case class ClassicalUnionFind(init: Int) extends UnionFind {
    var size = 0
    private val groups = new Array[Node](init)
    for (i <- 0 until init) {
      groups(i) = new Node(i)
    }

    override def find(x: Int): Int = {
      val node = groups(x)
      if (node.leader.name == x) {
        return x
      }
      return find(node.leader.name)
    }

    override def union(g1: Int, g2: Int): Unit = {
      val g1Root = find(g1)
      val g2Root = find(g2)
      val rand = new Random
      if (rand.nextBoolean()) {
        groups(g1Root).leader = groups(g2Root)
      } else {
        groups(g2Root).leader = groups(g1Root)
      }
    }
  }

}
