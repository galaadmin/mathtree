package mathtree;

import java.util.*;


public class Test {

	public enum Operator {
		ADD, SUBTRACT, MULTIPLE, DIVIDE
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

	private class Expression extends Term {
		private Operator operator;
		private Expression op1;
		private Expression op2;

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
			this.op1 = (Expression)op;
		}
		public void setOp2(Expression op) {
			this.op2 = op;
		}
		public void setOp2(Term op) {
			this.op1 = (Expression)op;
		}
	}


	public static void main(String args[]) {
		Test test = new Test();
		Expression expr = test.new Expression();
		Stack<Term> terms = new Stack<Term>();
		test.make_terms(args, terms, 0);
		test.make_expr(terms, expr);
		System.out.println("terms " + terms.toString());
		System.out.println("expression " + expr.toString());
	}
	
	private void make_expr(Stack<Term> terms, Expression expr) {
		System.out.println("in make_expr");
		System.out.println("first " + terms.get(0).isNumber());
		if(terms.get(0).isNumber() && terms.get(1).isNumber() && !terms.get(2).isNumber()) {
			expr.setOp1(new Term(terms.get(0).getNumValue()));
			expr.setOp2(new Term(terms.get(1).getNumValue()));
			expr.setOperator(terms.get(2).getOpValue());
		}
	}

	private void make_terms(String input[], Stack<Term> terms, int index) {
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
