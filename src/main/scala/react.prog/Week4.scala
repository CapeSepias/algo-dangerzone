package react.prog

import rx.lang.scala.Observable
import scala.concurrent._
import scala.concurrent.duration._

object Week4 {

  def main(args: Array[String]) = {

    val ticks = Observable.interval(1 second)
    val even = ticks.filter(_ % 2 == 1)
    val buffered = ticks.buffer(1, 2)

    val s = buffered.subscribe(println(_))

    readLine()

    s.unsubscribe()
  }
}
