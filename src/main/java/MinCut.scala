
import java.io.{InputStreamReader, BufferedReader}
import java.util.StringTokenizer

/**
 * Solution for PA#3
 * @author kperikov
 */
object MinCut {


  var br: BufferedReader = null
  var st: StringTokenizer = null
  val random = new Random(System.currentTimeMillis())
  val NUMBER_OF_RUNS = 1000

  def next: String = {
    while (st == null || !st.hasMoreTokens) {
      st = new StringTokenizer(br.readLine)
    }
    st.nextToken
  }

  def nextInt: Int = {
    Integer.parseInt(next)
  }


  def buildArrayWithAcc(acc: Array[Int], list: List[Int]): Array[Int] = {
    list match {
      case x :: xs => {
        acc(x - 1) += 1
        buildArrayWithAcc(acc, xs)
      }
      case _ => {
        acc
      }
    }
  }

  def buildArrayFromList(list: List[Int], size: Int): Array[Int] = {
    val array = new Array[Int](size)
    buildArrayWithAcc(array, list)
  }

  def printMatrix(g: Array[Array[Int]]): Unit = {
    val n = g.length
    val m = g(0).length
    for (i <- 0 until n) {
      for (j <- 0 until m) {
        print(g(i)(j) + " ")
      }
      println()
    }
  }

  def calculateListOfEdges(g: Array[Array[Int]]): Array[(Int, Int)] = {
    val n = g.length
    var len = 0
    for (i <- 0 until n) {
      for (j <- 0 until n) {
        len += g(i)(j)
      }
    }
    val ans = new Array[(Int, Int)](len)
    var pos = 0
    for (i <- 0 until n) {
      for (j <- 0 until n) {
        for (k <- 0 until g(i)(j)) {
          ans(pos) = (i, j)
          pos += 1
        }
      }
    }
//    for (i <- 0 until len) {
//      print(ans(i) + " ")
//    }
//    println
    ans
  }

  def runKargerAlgo(matrix: Array[Array[Int]], listOfEdges: Array[(Int, Int)]): Int = {
    val size = matrix.length
    size match {
      case s if s > 2 => {
        //        println("Matrix on begining of step")
        //        printMatrix(matrix)
        val edge = random.nextInt(listOfEdges.length)
        val firstVertex = listOfEdges(edge)._1
        val secondVertex = listOfEdges(edge)._2
        //        println("Contraction " + firstVertex + " to " + secondVertex)
        for (i <- 0 until size) {
          matrix(secondVertex)(i) += matrix(firstVertex)(i)
          matrix(i)(secondVertex) += matrix(i)(firstVertex)
          matrix(i)(i) = 0
        }
        //        println("Updated matrix")
        //        printMatrix(matrix)
        // run Karger algo, with reduced matrix
        val reducedMatrix = new Array[Array[Int]](size - 1)
        var posX = 0
        for (i <- 0 until size) {
          var posY = 0
          if (i != firstVertex) {
            val temp = new Array[Int](size - 1)
            for (j <- 0 until size) {
              if (j != firstVertex) {
                temp(posY) = matrix(i)(j)
                posY += 1
              }
            }
            reducedMatrix(posX) = temp
            posX += 1
          }
        }
        //        println("Reduced matrix")
        //        printMatrix(reducedMatrix)
        runKargerAlgo(reducedMatrix, calculateListOfEdges(reducedMatrix))
      }
      case _ => {
        // todo, we reduce matrix all the time, and when it's size became 2, we just could return answer explicitly
        //        println("Min cut is " + matrix(0)(1))
        matrix(0)(1)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    br = new BufferedReader(new InputStreamReader(System.in))
    val n = readInt()
    val arr = new Array[Array[Int]](n)
    for (i <- 1 to n) {
      val list = readLine().split(" ").map(_.toInt).toList
      arr(list.head - 1) = buildArrayFromList(list.tail, n)
    }
    //printMatrix(arr)
    var minVal = Int.MaxValue
    for (i <- 0 to NUMBER_OF_RUNS) {
      val copy = new Array[Array[Int]](n)
      for (j <- 0 until n) {
        val temp = new Array[Int](n)
        for (k <- 0 until n) {
          temp(k) = arr(j)(k)
        }
        copy(j) = temp
      }
      minVal = min(minVal, runKargerAlgo(copy, calculateListOfEdges(copy)))
    }
    println("Answer is " + minVal)
  }
}
