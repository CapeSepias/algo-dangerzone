package algo2

import java.io._
import java.util
import java.util.StringTokenizer

import scala.collection._
import scala.util.Random

/**
 * Solution for PA#2
 */
object ClusteringAlgoWithHamming {

  var out: PrintWriter = null
  var br: BufferedReader = null
  var st: StringTokenizer = null

  def main(args: Array[String]): Unit = {
    br = new BufferedReader(new FileReader("input2.txt"))
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

  val allMasks = new util.HashSet[Int]()

  def printMasks(n0: Int, n1: Int, mask: Int) {
    if (n0 == 0 && n1 == 0) {
//      out.println(Integer.toBinaryString(mask))
      allMasks.add(mask)
      return
    }
    val m = mask << 1
    if (n0 != 0) {
      printMasks(n0 - 1, n1, m)
    }
    if (n1 != 0) {
      printMasks(n0, n1 - 1, m | 1)
    }
  }

  def count(i: Int): Int = {
    val s = Integer.toBinaryString(i)
    var res = 0
    for (i <- 0 until s.length) {
      if (s.charAt(i) == '1') {
        res += 1
      }
    }
    return res
  }

  def solve: Int = {
    val numberOfNodes = nextInt
    val numberOfBits = nextInt
    printMasks(numberOfBits - 1, 1, 0)
    printMasks(numberOfBits - 2, 2, 0)
    val initialSet = new util.HashSet[Int]()
    val vertex = new util.HashMap[Int, Int]()
    for (i <- 0 until numberOfNodes) {
      val s = Integer.parseInt(next, 2)
      vertex.put(s, i)
      initialSet.add(s)
    }
    val edges = new util.HashSet[Edge]()
    val vertexes = new util.HashSet[Int]()
    val it = initialSet.iterator
    while (it.hasNext) {
      val node = it.next
      val it2 = allMasks.iterator
      while (it2.hasNext) {
        val mask = it2.next
        val xor = node ^ mask
        if (initialSet.contains(xor) && vertex.containsKey(node) && vertex.containsKey(xor)) {
          vertexes.add(vertex.get(node))
          vertexes.add(vertex.get(xor))
          edges.add(new Edge(Math.min(vertex.get(node), vertex.get(xor)), Math.max(vertex.get(node), vertex.get(xor)), count(mask)))
        }
      }
    }
//    val e = edges.iterator
//    while (e.hasNext) {
//      val edge: Edge = e.next
//      out.println(edge.from + " " + edge.to + " " + edge.cost)
//    }
    val X = numberOfNodes - vertexes.size()
    out.println(clusterSlowAlgo(numberOfNodes, edges.toArray(new Array[Edge](0))))
    return 0
  }

  def clusterSlowAlgo(c: Int, edges: Array[Edge]): Long = {
    val sortedEdges = edges.sorted
    var ind = 0
    var clusters = c
    val uf: UnionFind = new ClassicalUnionFind(clusters)
    while (ind < edges.length) {
      val edge = sortedEdges(ind)
      if (uf.find(edge.from) != uf.find(edge.to)) {
        uf.union(edge.from, edge.to)
        // merge cluster
        clusters -= 1
      }
      ind += 1
    }
    return clusters
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
