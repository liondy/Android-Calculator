package com.example.calculatingwombat.model;

import java.util.Stack;

public class Operand {
    private double prevValue;
    private String operator;
    private String operand;
    private double value;

    public Operand(String operator, String operand) {
        this.operand = operand;
        this.operator = operator;
        this.prevValue = 0d;
        this.value = 0d;
    }

    public void setPrevValue(double prevValue) {
        this.prevValue = prevValue;
    }

    public void calculate() {
        Stack<Character> operator = new Stack<>();
        Stack<Double> operand = new Stack<>();
        int it = 0;
        while (it < this.operand.length()) {
            char curr = this.operand.charAt(it);
            if (this.isNumber(curr)) {
                int limit = this.getNumberLimit(it);
                double num = Double.parseDouble(this.operand.substring(it, limit));
                it = limit;
                operand.push(num);
            } else {
                if (curr == '(')
                    operator.push(curr);
                else if (curr == ')') {
                    while (operator.peek() != '(') {
                        char operate = operator.pop();
                        double first = operand.pop();
                        double second = operand.pop();
                        if (operate == '/' && first == 0) {
                            throw new ArithmeticException();
                        }
                        operand.push(this.calculate(second, first, operate));
                    }
                    operator.pop();
                } else if (curr != ' ') {
                    while (!operator.isEmpty() && this.hasPrecedence(curr, operator.peek()) && operand.size() >= 2) {
                        char operate = operator.pop();
                        double first = operand.pop();
                        double second = operand.pop();
                        if (operate == '/' && first == 0) {
                            throw new ArithmeticException();
                        }
                        operand.push(this.calculate(second, first, operate));
                    }
                    operator.push(curr);
                }
                it++;
            }
        }

        while (!operator.isEmpty() && operand.size() >= 2) {
            char operate = operator.pop();
            double first = operand.pop();
            double second = operand.pop();
            if (operate == '/' && first == 0) {
                throw new ArithmeticException();
            }

            operand.push(this.calculate(second, first, operate));
        }

        this.value = operand.pop();
    }

    private boolean hasPrecedence(char op1, char op2) {
        if(op2 == '^') return true;
        if(op1 == '^' && op2 != '^') return false;
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/' || op1 == ':') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }

    private int getNumberLimit(int idx) {
        int end = idx + 1;
        int len = this.operand.length();
        while (end < len && this.isNumber(this.operand.charAt(end)))
            end++;

        return end;
    }

    private boolean isNumber(char num) {
        return num >= 48 && num <= 57;
    }

    private double calculate(double first, double second, char operator) {
        switch (operator) {
            case '+': return first + second;
            case '-': return first - second;
            case '*': return first * second;
            default: return first / second;
        }
    }

    public double getTotalValue() {
        return this.calculate(this.prevValue, this.value, this.operator.charAt(0));
    }
}
