-module(mathtree).

-export([run/1, parse/1]).

parse(Expression) ->
    io:fwrite("~p~n", [Expression]),
    run(make_terms(string:tokens(Expression, ", "), [])).

make_terms([], Ops) ->
    make_expr(Ops);
make_terms([H|T], Ops) ->
    case H of
	"+" -> make_terms(T, [add|Ops]);
	"-" -> make_terms(T, [subtract|Ops]);
	"*" -> make_terms(T, [multiple|Ops]);
	"/" -> make_terms(T, [divide|Ops]);
	N -> make_terms(T, [list_to_integer(N)|Ops])
    end.


make_expr([Op2, Op1, Op]) ->
    io:fwrite("~w~n", [{Op, Op1, Op2}]),
    {Op, Op1, Op2};
make_expr([Op2, Op1, Op|More]) ->
    make_expr([{Op, Op1, Op2}|More]).


run(N) when is_number(N) ->
    N;
run({add, Op1, Op2}) ->
    run(Op1) + run(Op2);
run({subtract, Op1, Op2}) ->
    run(Op1) - run(Op2);
run({multiple, Op1, Op2}) ->
    run(Op1) * run(Op2);
run({divide, Op1, Op2}) ->
    run(Op1) / run(Op2).

