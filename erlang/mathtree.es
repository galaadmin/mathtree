#!/usr/local/bin/escript

main(Tokens) ->
    Expressions = make_expr(make_terms(Tokens, []), []),
    %% io:fwrite("~w~n", [Expressions]),
    io:fwrite("result = ~w~n", [calculate(Expressions)]),
    init:stop().

%%
%% make_terms/2
%% build a list of terms from the list of tokens
%% finally turn those terms into expressions
%%
make_terms([], Out) -> lists:reverse(Out);
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
calculate(N) when is_number(N) ->
    N;
calculate({add, Op1, Op2}) ->
    calculate(Op1) + calculate(Op2);
calculate({subtract, Op1, Op2}) ->
    calculate(Op1) - calculate(Op2);
calculate({multiple, Op1, Op2}) ->
    calculate(Op1) * calculate(Op2);
calculate({divide, Op1, Op2}) ->
    calculate(Op1) div calculate(Op2).

