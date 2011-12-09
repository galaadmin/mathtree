package mathtree;

import java.util.*;


public class Test {

	public enum Operator {
		ADD, SUBTRACT, MULTIPLE, DIVIDE
	}

	private abstract class Element {

		public Boolean isNumber() {
			return true;
		}

		public Number getNumValue() {
			return null;
		}

		public Operator getOpValue() {
			return null;
		}
	}

	private class Term extends Element {
		private Object value;

		Term() { }

		Term(Number number) {
			value = number;
		}

		Term(Operator op) {
			value = op;
		}

		public String toString() {
			return value.toString();
		}

		public Boolean isNumber() {
			return value instanceof Number;
		}

		public Operator getOpValue() {
			return (Operator)value;
		}

		public Number getNumValue() {
			return (Number)value;
		}

		public Term getValue() {
			return (Term)value;
		}
	}

	private class Expression extends Element {
		private Operator operator;
		private Element op1;
		private Element op2;

		public Expression() {
			operator = null;
		}

		public Expression(Operator op) {
			operator = op;
		}

		public void setOperator(Operator op) {
			this.operator = op;
		}

		public void setOp1(Expression op) {
			this.op1 = op;
		}
		public void setOp1(Term op) {
			this.op1 = (Element)op;
		}

		public void setOp2(Expression op) {
			this.op2 = op;
		}
		public void setOp2(Term op) {
			this.op2 = (Element)op;
		}
	}


	public static void main(String args[]) {
		Test test = new Test();
		Expression expr = test.new Expression();
		Stack<Element> terms = new Stack<Element>();
		test.make_terms(args, terms, 0);
		Collections.reverse(terms);
		test.make_expr(terms, expr);
		System.out.println("terms " + terms.toString());
		System.out.println("expression " + expr.toString());
	}

	private void make_expr(Stack<Element> terms, Expression expr) {
		System.out.println("in make_expr");
		System.out.println("first term " + terms.peek().toString() + " is a number = " + terms.peek().isNumber());
//		if(terms.peek().isNumber() && terms.peek().isNumber() && !terms.peek().isNumber()) {
//			expr.setOp1(new Term(terms.pop().getNumValue()));
//			expr.setOp2(new Term(terms.pop().getNumValue()));
//			expr.setOperator(terms.pop().getOpValue());
//			terms.push(expr);
//
//			if(terms.size() != 0) {
//				make_expr(terms, expr);
//			}
//		}
	}

	private void make_terms(String input[], Stack<Element> terms, int index) {
		while(index < input.length) {
			if(input[index].equals("+")) {
				terms.push(new Term(Operator.ADD));
			}
			else if(input[index].equals("-")) {
				terms.push(new Term(Operator.SUBTRACT));
			}
			else if(input[index].equals("*")) {
				terms.push(new Term(Operator.DIVIDE));
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
