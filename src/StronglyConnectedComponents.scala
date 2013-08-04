import scala.io.Source._

/**
 * @author kperikov
 */
object StronglyConnectedComponents {

  var used: Array[Boolean] = null
  var order: List[Int] = null
  var components: List[Int] = null

  def findOrder(n: Int, graph: Array[List[Int]]) = {
    order = Nil
    used = new Array[Boolean](n + 1)
    for (i <- 1 to n) {
      if (!used(i)) {
        dfs1(i, graph)
      }
    }
  }

  def dfs1(vertex: Int, g: Array[List[Int]]): Unit = {
    used(vertex) = true
    for (v <- g(vertex)) {
      if (!used(v)) {
        dfs1(v, g)
      }
    }
    order = vertex :: order
  }

  def dfs2(vertex: Int, g: Array[List[Int]]): Unit = {
    used(vertex) = true
    components = vertex :: components
    for (v <- g(vertex)) {
      if (!used(v)) {
        dfs2(v, g)
      }
    }
  }

  def findComponents(n: Int, g: Array[List[Int]]): List[Int] = {
    used = new Array[Boolean](n + 1)
    components = Nil
    var ans: List[Int] = Nil
    for (i <- 1 to n) {
      val vertex = order(n - i)
      if (!used(vertex)) {
        dfs2(vertex, g)
        ans = components.size :: ans
        components = Nil
      }
    }
    ans
  }

  def main(args: Array[String]): Unit = {
    val lines = fromFile("E:/file.txt").getLines
    val n = readInt()
    val arr = new Array[List[Int]](n + 1)
    for (i <- 1 to n) {
      arr(i) = Nil
    }
    for (line <- lines) {
      val edge = line.split(" ").map(_.toInt).toList
      arr(edge(0)) = edge(1) :: arr(edge(0))
    }
    findOrder(n, arr)
    findComponents(n, arr).sorted.foreach(println)
  }
}
