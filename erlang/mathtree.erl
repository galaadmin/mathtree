-module(mathtree).

-export([run/1, parse/1]).

parse(Expression) ->
    %% get a list of white space sperated tokens
    Tokens = string:tokens(Expression, " "),
    Expressions = make_terms(Tokens, []),
    io:fwrite("~w~n", [Expressions]),
    run(Expressions).

%%
%% make_terms/2
%% build a list of terms from the list of tokens
%% finally turn those terms into expressions
%%
make_terms([], Out) -> make_expr(lists:reverse(Out), []);
make_terms(["+"|T], Out) -> make_terms(T, [add|Out]);
make_terms(["-"|T], Out) -> make_terms(T, [subtract|Out]);
make_terms(["*"|T], Out) -> make_terms(T, [multiple|Out]);
make_terms(["/"|T], Out) -> make_terms(T, [divide|Out]);
make_terms([Number|T], Out) -> make_terms(T, [list_to_integer(Number)|Out]).


%%
%% make_expr/2
%% terms are either a number or an atom denoting an operation
%% create nested expressions in the form of
%% {operator, operand-1, operand-2}
%%
make_expr([Term|Terms], Numbers) when is_number(Term) ->
    make_expr(Terms, [Term|Numbers]);
make_expr([Term|Terms], [N1, N2|Numbers]) -> %% it is an atom
    case Terms of
	[] -> {Term, N2, N1};
	_ ->
	    make_expr(Terms, [{Term, N2, N1}|Numbers])
	end.

%%
%% calculate the expression
%%
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

