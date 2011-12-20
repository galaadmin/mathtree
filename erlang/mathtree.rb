data = ['subtract',['multiply',['add',['add',['multiply',['subtract',['add',7,3],-15],['subtract',127,55]],31],['multiply',82,97]],91],12]
# data = { operator => 'subtract', op1 => 33, op2 => 15 }

def calculate(data)
    # puts "data: " + data.join(" ")
    if data.is_a?(Numeric)
	data
    elsif data[0] == 'add'
    	calculate(data[1]) + calculate(data[2])
    elsif data[0] == 'subtract'
    	calculate(data[1]) - calculate(data[2])
    elsif data[0] == 'multiply'
    	calculate(data[1]) * calculate(data[2])
    elsif data[0] == 'divide'
    	calculate(data[1]) / calculate(data[2])
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
	    output << 'add'
	elsif element == "-"
	    output << 'subtract'
	elsif element == "*"
	    output << 'multiply'
	elsif element == "/"
	    output << 'divide'
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
	    op2 = expressions.pop()
	    op1 = expressions.pop()
	    expressions << [top, op1, op2]
	end
	make_expr(terms, expressions)
    else
	expressions
    end
end

# data = make_expr(make_terms(ARGV, Array.new()), Array.new())
# puts "Expressions: " + data.join(" ")
puts calculate(data)

