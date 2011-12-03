-module(mathtree).

-export([run/1, parse/1]).

parse(Expression) ->
    io:fwrite("~p~n", [Expression]),
    Tokens = string:tokens(Expression, ", "),
    io:fwrite("~p~n", [Tokens]),
    Term = make_terms(Tokens, []),
    io:fwrite("~w~n", [Term]),
    run(Term).

make_terms([], Out) -> make_expr(lists:reverse(Out));
make_terms(["+"|T], Out) -> make_terms(T, [add|Out]);
make_terms(["-"|T], Out) -> make_terms(T, [subtract|Out]);
make_terms(["*"|T], Out) -> make_terms(T, [multiple|Out]);
make_terms(["/"|T], Out) -> make_terms(T, [divide|Out]);
make_terms([Number|T], Out) -> make_terms(T, [list_to_integer(Number)|Out]).


make_expr([Op, Op1, Op2|Terms]) when is_atom(Op), is_number(Op1), is_number(Op2) ->
    io:fwrite("3 ~w ~w~n", [Op, Terms]),
    {Op, Op1, Op2};
make_expr([Op, Op1, Op2|Terms]) when is_atom(Op), is_number(Op1) ->
    io:fwrite("2 ~w ~w~n", [Op, [Op2|Terms]]),
    {Op, Op1, make_expr([Op2|Terms])};
make_expr([Op, Op1, Op2|Terms]) when is_atom(Op) ->
    Expr =  make_expr([Op1, Op2|Terms]),
    io:fwrite("1 ~w ~w ~w~n", [Op, [Op1,Op2|Terms], Expr]),
    {Op, Expr, make_expr(lists:sublist(Terms, tuple_size(Expr), 50))};
make_expr([Number|Terms]) ->
    Number.


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

