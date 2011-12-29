OPERATOR = "operator"
OP1 = "op1"
OP2 = "op2"

ADD = "add"
SUBTRACT = "subtract"
MULTIPLY = "multiply"
DIVIDE = "divide"

def calculate(data)
  if data.is_a?(Numeric)
    data
  elsif data.is_a?(Hash)
    if data[OPERATOR] == ADD
	    calculate(data[OP1]) + calculate(data[OP2])
    elsif data[OPERATOR] == SUBTRACT
	    calculate(data[OP1]) - calculate(data[OP2])
    elsif data[OPERATOR] == MULTIPLY
	    calculate(data[OP1]) * calculate(data[OP2])
    elsif data[OPERATOR] == DIVIDE
	    calculate(data[OP1]) / calculate(data[OP2])
    end
  else
    puts "how did I get here" + data.class.name
  end
end

def make_terms(input, output)
  if input.length == 0
    return output
  else
    element = input.shift
    number = element.to_i
    if element.match(/[0-9]+/)
	    output << element.to_i
    elsif element == "+"
	    output << ADD
    elsif element == "-"
	    output << SUBTRACT
    elsif element == "x"
	    output << MULTIPLY
    elsif element == "/"
	    output << DIVIDE
    end
    make_terms(input, output)
  end
end

def make_expr(terms, expression)
  top = terms.shift
  if top
    if top.is_a?(Numeric)
      # puts "number: " + top.to_s + " " + expression.to_s
	    expression << top
    else
      # puts "term: " + top + " " + expression.to_s
	    expression << { OPERATOR => top, OP2 => expression.pop(), OP1 => expression.pop() }
    end
    make_expr(terms, expression)
  else
    # showtree(expression)
    expression.last
  end
end

0.upto(999) {
  instring = Array.new(ARGV)
  start_time = Time.now.usec
  data = make_expr(make_terms(instring, Array.new()), Array.new())
  # puts "Expressions: " + data.join(" ")
  result = calculate(data)
  end_time = Time.now.usec
  puts "Ruby\ttime = " + (end_time - start_time).to_s + "\tresult = " + result.to_s
}
