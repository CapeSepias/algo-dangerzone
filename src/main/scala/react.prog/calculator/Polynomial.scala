package react.prog.calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal {
      b() * b() - 4.0d * a() * c()
    }
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Signal {
      delta() match {
        case d if d < 0.0d => Set()
        case d if Math.abs(d) <= 1e-5 => Set((-b() * 1.0d) / (2.0d * a()))
        case d => Set((-b() + Math.sqrt(d)) / (2.0d * a()), (-b() - Math.sqrt(d)) / (2.0d * a()))
      }
    }
  }
}
