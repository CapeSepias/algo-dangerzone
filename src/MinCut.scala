
import java.io.{InputStreamReader, BufferedReader}
import java.util.StringTokenizer
import scala.math._

/**
 * Solution for PA#3
 * @author kperikov
 */
object MinCut {


  var br: BufferedReader = null
  var st: StringTokenizer = null
  val NUMBER_OF_RUNS = 100

  def next: String = {
    while (st == null || !st.hasMoreTokens) {
      st = new StringTokenizer(br.readLine)
    }
    st.nextToken
  }

  def nextInt: Int = {
    Integer.parseInt(next)
  }

  def runKargerAlgo(ints: Array[List[Int]]): Int = {
    val adjList = ints

    0
  }

  def main(args: Array[String]): Unit = {
    br = new BufferedReader(new InputStreamReader(System.in))
    val n = 200
    val adj = new Array[List[Int]](n)
    for (i <- 1 to n) {
      val vertex = nextInt
      val list = next.split(" ").map(_.toInt).toList
      adj(vertex - 1) = list
    }
    var minVal = Int.MaxValue
    for (i <- 0 to NUMBER_OF_RUNS) {
      minVal = min(minVal, runKargerAlgo(adj))
    }
    println(minVal)
  }
}
