/**
 * Mengenang penderitaan saya dalam mengerjakan hal gila
 * Tapi tidak sesuai spec....
 */

package com.example.calculatingwombat.model.utils;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Stack;

@Deprecated
public class ShuntingYard {
    private StringBuilder output;
    private Stack<String> stack;
    private String expression;

    private LexicographicParser lexer;

    private enum Operator {
        ADD(3, Operator.LEFT),
        SUBTRACT(3, Operator.LEFT),
        MULTIPLY(4, Operator.LEFT),
        DIVIDE(4, Operator.LEFT),
        POWER(5, Operator.LEFT);

        static final int LEFT = 0;
        static final int RIGHT = 1;

        final int precedence;
        final int associativity;

        Operator(int p, int a) {
            precedence = p;
            associativity = a;
        }
    }

    private Map<String, Operator> ops = new HashMap<String, Operator>() {{
        put("+", Operator.ADD);
        put("-", Operator.SUBTRACT);
        put("*", Operator.MULTIPLY);
        put("/", Operator.DIVIDE);
        put("^", Operator.POWER);
    }};

    public ShuntingYard(String expression) {
        this.output = new StringBuilder();
        this.stack = new Stack<>();
        this.expression = expression;

        lexer = new LexicographicParser(
                new ByteArrayInputStream(this.expression.getBytes())
        );
    }

    public String evaluate() {
        int id;
        String token;

        while ((id = lexer.nextSymbol()) != LexicographicParser.EOF) {
            token = lexer.stringify(id);

            if (token == null) {
                continue;
            }

            if (LexicographicParser.isNumber(token)) {
                output.append(token).append(' ');
            }

            if (this.isOperator(token)) {
                while (!stack.isEmpty() && this.isOperator(stack.peek()) && this.isHigherPrec(token)) {
                    output.append(stack.pop()).append(' ');
                }

                stack.push(token);
            }

            if (token.charAt(0) == '(') {
                stack.push(token);
            }

            if (token.charAt(0) == ')') {
                try {
                    while (!stack.isEmpty() && stack.peek().charAt(0) != '(') {
                        output.append(stack.pop()).append(' ');
                    }
                } catch (Exception e) {
                    throw new InputMismatchException();
                }

                if (stack.isEmpty())
                    throw new InputMismatchException();

                stack.pop();
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().charAt(0) == ')' || stack.peek().charAt(0) == '(') {
                throw new InputMismatchException();
            }

            output.append(stack.pop()).append(' ');
        }

        return output.toString();
    }

    private boolean isHigherPrec(String token) {
        return isLeftAssociative(token) && precedence(token) <= precedence(stack.peek()) || isRightAssociative(token) && precedence(token) < precedence(stack.peek());
    }

    private int precedence(String token) {
        return (ops.get(token)!= null) ? ops.get(token).precedence : -1;
    }

    private boolean isOperator(String token) {
        return ops.containsKey(token);
    }

    private boolean isLeftAssociative(String token) {
        return ops.get(token)!= null && ops.get(token).associativity == Operator.LEFT;
    }

    private boolean isRightAssociative(String token) {
        return ops.get(token)!= null && ops.get(token).associativity == Operator.RIGHT;
    }
}