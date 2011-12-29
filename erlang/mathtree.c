//
//  main.c
//  mathtree
//
//  Created by Ed Schneeflock on 27/Dec/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

/*
 * File:   main.cpp
 * Author: eschneef
 *
 * Created on December 27, 2011, 7:27 PM
 */

#include <stdlib.h>
#include <sys/time.h>
#include <stdio.h>
#include <string.h>

typedef enum {
    Add, Subtract, Multiply, Divide
} Ops;

typedef enum {
    Op, Value
} TermType;

typedef struct mathterm {
    TermType kind;
    Ops operation;
    long value;
} MathType;

typedef enum { True, False } Bool;

typedef struct mathexpr {
    Bool number;
    long value;
    Ops op;
    struct mathexpr *op1;
    struct mathexpr *op2;
} ExprType;

MathType *make_terms(int counter, char** tokens);
ExprType *make_expr();
long calculate(ExprType *expr);

int termtop;
MathType *terms;

int main(int argc, char** argv) {
    struct timeval t;

    for(int i=0; i<1000; i++) {
        gettimeofday(&t, NULL);
        long start_time = t.tv_usec;
        terms = make_terms(argc, argv);
        termtop = argc - 1;
        ExprType *expr = make_expr();
        long result = calculate(expr);
        gettimeofday(&t, NULL);
        long end_time = t.tv_usec;
        printf("C\ttime = %ld\tresult = %ld\n", end_time - start_time, result);
    }
    return 0;
}

MathType *make_terms(int count, char** tokens) {
    MathType *termlist = (MathType *) malloc(count * sizeof (MathType));
    for (int i = 1, j = 0; i < count; i++, j++) {
        if (strcmp(tokens[i], "+") == 0) {
            termlist[j].kind = Op;
            termlist[j].operation = Add;
        } else if (strcmp(tokens[i], "-") == 0) {
            termlist[j].kind = Op;
            termlist[j].operation = Subtract;
        } else if (strcmp(tokens[i], "x") == 0) {
            termlist[j].kind = Op;
            termlist[j].operation = Multiply;
        } else if (strcmp(tokens[i], "/") == 0) {
            termlist[j].kind = Op;
            termlist[j].operation = Divide;
        } else {
            termlist[j].kind = Value;
            termlist[j].value = atol(tokens[i]);
        }
    }
    return termlist;
}

ExprType *make_expr() {
    MathType top = terms[--termtop];
    ExprType *result = NULL;

    if (top.kind == Op) {
        result = (ExprType *) malloc(sizeof (ExprType));
        result->number = False;
        result->op = top.operation;
        result->op2 = make_expr();
        result->op1 = make_expr();
    } else if (top.kind == Value) {
        result = (ExprType *) malloc(sizeof (ExprType));
        result->number = True;
        result->value = top.value;
    }

    return result;
}

long calculate(ExprType *expr) {
    long result = 0;

    if (expr->number == True) {
        result = expr->value;
    } else {
        switch (expr->op) {
            case Add:
                result = calculate(expr->op1) + calculate(expr->op2);
                break;
            case Subtract:
                result = calculate(expr->op1) - calculate(expr->op2);
                break;
            case Multiply:
                result = calculate(expr->op1) * calculate(expr->op2);
                break;
            case Divide:
                result = calculate(expr->op1) / calculate(expr->op2);
                break;
        }
    }

    return result;
}

