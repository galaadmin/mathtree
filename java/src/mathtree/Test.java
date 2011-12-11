package mathtree;

import java.util.*;


public class Test {

	public enum Operator {
		ADD, SUBTRACT, MULTIPLE, DIVIDE, NUMBER
	}


	private class Term {
		private Object value;

		Term() { }

		Term(Number number) {
			value = number;
		}

		Term(Operator op) {
			value = op;
		}

		Term(Expression expr) {
			value = expr;
		}

		public Term(Term term) {
			value = term;
		}

		public String toString() {
			return value.toString();
		}

		public Boolean isNumber() {
			return value instanceof Number;
		}
		
		public Boolean isOperator() {
			return value instanceof Operator;
		}

		public Operator getOpValue() {
			return (Operator)value;
		}

		public Integer getNumValue() {
			Integer result = 0;
			if(value instanceof Integer) {
				result = (Integer) value;
			}
			return result;
		}

		public Expression getExprValue() {
			return (Expression)value;
		}

		public Term getValue() {
			return (Term)value;
		}
	}

	public abstract class Expression {
		
		public Expression() { }

		public Expression(Operator op) { }

		public abstract Operator getOperator();

		public abstract Integer getValue();
	}
	
	private class SimpleExpression extends Expression {
		private Operator operator;
		private Number op;

		public SimpleExpression() {
			operator = null;
		}

		public SimpleExpression(Number n) {
			operator = Operator.NUMBER;
			op = n;
		}

		public Boolean isNumber() {
			return true;
		}

		public Integer getValue() {
			return (Integer) op;
		}
		
		public Operator getOperator() {
			return operator;
		}
	}

	private class ComplexExpression extends Expression {
		private Operator operator;
		private Expression op1;
		private Expression op2;

		public ComplexExpression() {
			operator = null;
		}

		public ComplexExpression(Operator op) {
			operator = op;
		}

		public ComplexExpression(Expression makeExpr) {
			// TODO Auto-generated constructor stub
		}

		public Operator getOperator() {
			return operator;
		}

		public Expression getOp1() {
			return op1;
		}
		public Expression getOp2() {
			return op2;
		}
		
		public void setOp1(ComplexExpression expr) {
			op1 = expr;
		}
		public void setOp2(ComplexExpression expr) {
			op2 = expr;
		}

		public void setOp2(Expression expr) {
			op2 = expr;
		}

		public void setOp1(Expression expr) {
			op1 = expr;
		}
		
		public Integer getValue() {
			return null;
		}
	}
 

	private static Stack<Term> terms;
	private static Expression expr;


	public static void main(String args[]) {
		Test test = new Test();
		terms = new Stack<Term>();
		test.make_terms(args, 0);
		expr = test.make_expr();
		Integer result = test.calculate(expr);
		System.out.println("result = " + result.toString());
		System.out.println("terms " + terms.toString());
		System.out.println("expression " + expr.toString() + " = " + result.toString());
	}


	public Integer calculate(Expression expr) {
		Integer result = 0;
		Integer n1, n2;
		if(expr.getOperator() == Operator.NUMBER) {
			result = expr.getValue();
		}
		else if(expr.getOperator() == Operator.ADD) {
			n2 = calculate(((ComplexExpression) expr).getOp2());
			n1 = calculate(((ComplexExpression) expr).getOp1());
			result = n1 + n2;
		}
		else if(expr.getOperator() == Operator.SUBTRACT) {
			n2 = calculate(((ComplexExpression) expr).getOp2());
			n1 = calculate(((ComplexExpression) expr).getOp1());
			result = n1 - n2;
		}
		else if(expr.getOperator() == Operator.MULTIPLE) {
			n2 = calculate(((ComplexExpression) expr).getOp2());
			n1 = calculate(((ComplexExpression) expr).getOp1());
			result = n1 * n2;
		}
		else if(expr.getOperator() == Operator.DIVIDE) {
			n2 = calculate(((ComplexExpression) expr).getOp2());
			n1 = calculate(((ComplexExpression) expr).getOp1());
			result = n1 / n2;
		}

		return result;
	}
	
	
	private Expression make_expr() {
		Expression result = null;
		System.out.println("in make_expr with " + terms.toString());
		System.out.println("first term " + terms.peek().toString() + " is a number = " + terms.peek().isNumber().toString());
		Term top = terms.pop();
		if(top.isOperator()) {
			ComplexExpression expr = new ComplexExpression(top.getOpValue());
			expr.setOp2(make_expr());
			expr.setOp1(make_expr());
			result = expr;
		}
		else if(top.isNumber()) {
			result = new SimpleExpression(top.getNumValue());
		}
		return result;
	}

	private void make_terms(String input[], int index) {
		while(index < input.length) {
			if(input[index].equals("+")) {
				terms.push(new Term(Operator.ADD));
			}
			else if(input[index].equals("-")) {
				terms.push(new Term(Operator.SUBTRACT));
			}
			else if(input[index].equals("*")) {
				terms.push(new Term(Operator.MULTIPLE));
			}
			else if(input[index].equals("/")) {
				terms.push(new Term(Operator.DIVIDE));
			}
			else {
				terms.push(new Term(Integer.parseInt(input[index])));
			}
			index++;
		}
	}
}
