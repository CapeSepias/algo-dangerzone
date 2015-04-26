package reactive.programming.getting.started

import reactive.programming.common._

object Lists {
  /** *
    * @param xs A list of natural numbers
    * @return The sum of all elements in `xs`
    */
  def sum(xs: List[Int]): Int = {
    xs match {
      case x :: xs => return x + sum(xs)
      case Nil => 0
    }
  }

  /**
   *
   * @param xs A list of natural numbers
   * @return The largest element in `xs`
   * @throws java.util.NoSuchElementException if `xs` is an empty list
   */
  def max(xs: List[Int]): Int = {
    xs match {
      case x :: Nil => return x
      case x :: xs => return Math.max(x, max(xs))
      case Nil => throw new java.util.NoSuchElementException("List is empty!")
    }
  }
}
