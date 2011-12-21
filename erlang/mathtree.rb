OPERATOR = "operator"
OP1 = "op1"
OP2 = "op2"

ADD = "add"
SUBTRACT = "subtract"
MULTIPLY = "multiply"
DIVIDE = "divide"

def calculate(data)
    print "data: "
    if data.is_a?(Numeric)
	puts "Numberic " + data.to_s
    else
	puts
	data.each{|key, value| puts "   Hash " + key.to_s + " => " + value.to_s }
    end
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
	number = element.to_i unless 
	if element.match(/[0-9]+/)
	    output << element.to_i
	elsif element == "+"
	    output << ADD
	elsif element == "-"
	    output << SUBTRACT
	elsif element == "*"
	    output << MULTIPLY
	elsif element == "/"
	    output << DIVIDE
	end
	make_terms(input, output)
    end
end

def make_expr(terms, expressions)
    top = terms.shift
    if top
	if top.is_a?(Numeric)
	    expressions << top
	else
	    expressions << { OPERATOR => top, OP2 => expressions.pop(), OP1 => expressions.pop() }
	end
	make_expr(terms, expressions)
    else
	expressions
    end
end

start_time = Time.now.usec
data = make_expr(make_terms(ARGV, Array.new()), Array.new())
# puts "Expressions: " + data.join(" ")
result = calculate(data[0])
end_time = Time.now.usec
puts "Time = " + (end_time - start_time).to_s + " Result = " + result.to_s

