package algo1

/**
 * Solution for PA#2
 */
object QuickSort {

  var result = 0

  def chooseFirstPivot(numbers: Array[Int], start: Int, end: Int): Int = {
    start
  }

  def chooseLastPivot(numbers: Array[Int], start: Int, end: Int): Int = {
    end
  }

  def chooseMedianPivot(numbers: Array[Int], start: Int, end: Int): Int = {
    def length: Int = {
      end - start + 1
    }
    def getMiddleIndex(length: Int): Int = {
      length match {
        case l if l % 2 == 0 => start + length / 2 - 1
        case _ => start + length / 2
      }
    }
    numbers.indexWhere(_ == List(numbers(start), numbers(end), numbers(getMiddleIndex(length))).sorted.apply(1))
  }

  def partitionNumbers(numbers: Array[Int], position: Int, start: Int, end: Int): (Int, Int, Int) = {
    result += (end - start)
    val pivot = numbers(position)
    numbers(position) = numbers(start)
    numbers(start) = pivot
    var i = start + 1
    for (j <- start + 1 to end) {
      if (numbers(j) < pivot) {
        val tmp = numbers(j)
        numbers(j) = numbers(i)
        numbers(i) = tmp
        i = i + 1
      }
    }
    val tmp = numbers(start)
    numbers(start) = numbers(i - 1)
    numbers(i - 1) = tmp
    (start, i - 1, end)
  }

  def calculateComparisons(pivot: (Array[Int], Int, Int) => Int, numbers: Array[Int], start: Int, end: Int): Unit = {
    val n = end - start
    n match {
      case 0 => return
      case _ => {
        if (start < numbers.length && end < numbers.length && start <= end) {
          val positions = partitionNumbers(numbers, pivot(numbers, start, end), start, end)
          calculateComparisons(pivot, numbers, positions._1, positions._2 - 1)
          calculateComparisons(pivot, numbers, positions._2 + 1, positions._3)
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val n = readInt
    val numbers1 = new Array[Int](n)
    val numbers2 = new Array[Int](n)
    val numbers3 = new Array[Int](n)
    for (i <- 0 to n - 1) {
      numbers1(i) = readInt
      numbers2(i) = numbers1(i)
      numbers3(i) = numbers1(i)
    }
    result = 0
    calculateComparisons(chooseFirstPivot, numbers1, 0, n - 1)
    println("Comparisons with first element pivot " + result)
    result = 0
    calculateComparisons(chooseLastPivot, numbers2, 0, n - 1)
    println("Comparisons with last element pivot " + result)
    result = 0
    calculateComparisons(chooseMedianPivot, numbers3, 0, n - 1)
    println("Comparisons with median element pivot " + result)
    for (i <- 0 to n - 1) {
      print(numbers1(i) + " ")
    }
    println
  }
}
