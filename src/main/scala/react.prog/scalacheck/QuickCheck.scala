package reactive.programming.scalacheck

import reactive.programming.common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._


abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min of 3") = forAll { (a1: Int, a2: Int, a3: Int) =>
    val h1 = insert(a1, empty)
    val h2 = insert(a2, h1)
    val h3 = insert(a3, h2)
    findMin(h2) == Math.min(a1, a2)
    findMin(h3) == Math.min(a1, Math.min(a2, a3))
  }

  property("delete") = forAll { (a: Int, b: Int) =>
    val h1 = insert(a, empty)
    val h2 = insert(b, h1)
    val h3 = deleteMin(h2)
    val h4 = deleteMin(h3)
    isEmpty(h4) == true
  }

  property("meld") = forAll { (a: Int, b: Int, c: Int, d: Int) =>
    val h1 = insert(a, empty)
    val h2 = insert(b, h1)
    val h3 = insert(c, empty)
    val h4 = insert(d, h3)
    val min1 = findMin(h2)
    val min2 = findMin(h4)
    val h5 = meld(h2, h4)
    findMin(h5) == Math.min(min1, min2)
  }

  property("random") = forAll { a: List[Int] =>
    val sorted = a.sorted
    var heap = empty
    for (i <- 0 until a.length) {
      heap = insert(a(i), heap)
    }
    import scala.collection.mutable.ListBuffer
    val list = new ListBuffer[Int]()
    while (!isEmpty(heap)) {
      list.append(findMin(heap))
      heap = deleteMin(heap)
    }
    list.length == sorted.length
    list == sorted
  }
}
