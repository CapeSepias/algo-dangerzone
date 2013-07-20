/**
 * @author kperikov
 */
object QuickSort {

  def chooseFirstPivot(numbers: Array[Int], start: Int, end: Int): (Int, Int) = {
    (numbers(0), 0)
  }

  def partitionNumbers(numbers: Array[Int], position: Int, start: Int, end: Int): (Int, Int, Int) = {
    val pivot = numbers(position)
    numbers(position) = numbers(start)
    numbers(start) = pivot
    var i = start + 1
    for (j <- start + 1 to end - 1) {
      if (numbers(j) < pivot) {
        val tmp = numbers(j)
        numbers(j) = numbers(i)
        numbers(i) = tmp
        i = i + 1
      }
    }
    val tmp = numbers(start)
    numbers(start) = numbers(i)
    numbers(i) = tmp
    (start, i, end)
  }

  def calculateComparisons(numbers: Array[Int], result: Int, start: Int, end: Int): Int = {
    val n = end - start
    n match {
      case 0 => result
      case _ => {
        val newResult = result + n
        val p = chooseFirstPivot(numbers, start, end)
        val positions = partitionNumbers(numbers, p._2, start, end)
        calculateComparisons(numbers, newResult, positions._1, positions._2 - 1)
        calculateComparisons(numbers, newResult, positions._2, positions._3)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val n = readInt
    val numbers = new Array[Int](n)
    for (i <- 0 to n - 1) {
      numbers(i) = readInt
    }
    println("Comparisons with first element pivot " + calculateComparisons(numbers, 0, 0, n - 1))
    for (i <- 0 to n - 1) {
      print(numbers(i) + " ")
    }
    println

  }
}
