package react.prog.calculator

sealed abstract class Expr

final case class Literal(v: Double) extends Expr

final case class Ref(name: String) extends Expr

final case class Plus(a: Expr, b: Expr) extends Expr

final case class Minus(a: Expr, b: Expr) extends Expr

final case class Times(a: Expr, b: Expr) extends Expr

final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  def computeValues(namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    for {
      expr <- namedExpressions
    } yield (expr._1, Signal(eval(expr._2(), namedExpressions)))
  }


  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double = {
    expr match {
      case Literal(v) => v
      case Ref(name) =>
        val e = getReferenceExpr(name, references)
        e match {
          case Ref(nm) => if (nm.equals(name)) Double.NaN else eval(e, references.filter(x => x.equals(name)))
          case _ => eval(e, references)
        }
      //        val filtered = references.filter(x => x.equals(name))
      case Plus(a, b) => eval(a, references) + eval(b, references)
      case Minus(a, b) => eval(a, references) - eval(b, references)
      case Times(a, b) => eval(a, references) * eval(b, references)
      case Divide(a, b) => eval(a, references) / eval(b, references)
    }
  }

  /** Get the Expr for a referenced variables.
    * If the variable is not known, returns a literal NaN.
    */
  private def getReferenceExpr(name: String,
                               references: Map[String, Signal[Expr]]) = {
    references.get(name) match {
      case Some(x) => x()
      case None => Literal(Double.NaN)
    }
    //    references.get(name).fold[Expr] {
    //      Literal(Double.NaN)
    //    } { exprSignal =>
    //      exprSignal()
    //    }
  }
}