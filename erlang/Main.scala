/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mathtree

object Main {

  object Op extends Enumeration {
    type Op = Value
    val Add, Subtract, Multiply, Divide = Value
  }
  import Op._

  trait Term
  case class Number(value: Int) extends Term {
    val kind = 1
  }
  case class Operator(value: Op) extends Term {
    val kind = 2
  }

  trait Expression
  case class SimpleExpr(value: Int) extends Expression {
    val kind = 1
  }
  case class ComplexExpr(op: Operator, op1: Expression, op2: Expression) extends Expression {
    val kind = 2
  }
  
  def main(args: Array[String]): Unit = {
    for(i <- 0 to 999) {
      val start_time = System.nanoTime();
      val terms = make_terms(args, List());
      val expr = make_expr(terms, List());
      val result = calculate(expr)
      val end_time = System.nanoTime();
      println("Scala\ttime = " + (end_time - start_time)/1000 + "\tresult = " + result);
    }
  }

  def make_terms(args: Array[String], terms: List[Term]): List[Term] = {
    val arg = args.head;
    val next = arg match {
      case "+" => new Operator(Add)
      case "-" => new Operator(Subtract)
      case "x" => new Operator(Multiply)
      case "/" => new Operator(Divide)
      case n => new Number(arg.toInt)
    }
    if(args.tail.size == 0) {
      (next :: terms).reverse
    }
    else {
      make_terms(args.tail, next :: terms)
    }
  }

  def make_expr(terms: List[Term], stack: List[Expression]): Expression = {
    val top =terms.head
    top match {
      case x: Operator =>
        if(terms.tail.isEmpty) {
          new ComplexExpr(x, stack(1), stack(0))
        }
        else {
          make_expr(terms.tail, new ComplexExpr(x, stack(1), stack(0)) :: stack.drop(2))
        }
      case x: Number =>
        make_expr(terms.tail, new SimpleExpr(x.asInstanceOf[Number].value) :: stack)
    }
  }

  def calculate(expr: Expression): Int = {
    if(expr.isInstanceOf[SimpleExpr]) {
      expr.asInstanceOf[SimpleExpr].value
    }
    else {
      val thisexpr: ComplexExpr = expr.asInstanceOf[ComplexExpr]
      val thisop: Op = thisexpr.op.value
      thisop match {
        case Add =>
          calculate(thisexpr.op1) + calculate(thisexpr.op2)
        case Subtract =>
          calculate(thisexpr.op1) - calculate(thisexpr.op2)
        case Multiply =>
          calculate(thisexpr.op1) * calculate(thisexpr.op2)
        case Divide =>
          calculate(thisexpr.op1) / calculate(thisexpr.op2)
      }
    }
  }
}
