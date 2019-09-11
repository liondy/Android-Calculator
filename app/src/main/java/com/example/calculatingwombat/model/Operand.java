package com.example.calculatingwombat.model;

import java.util.EmptyStackException;
import java.util.Stack;

public class Operand {
    private double prevValue;
    private String operator;
    private String origOperand;
    private String postfixOperand;
    private double value;

    public Operand(String operator, String origOperand, String postfixOperand) {
        this.origOperand = origOperand;
        this.postfixOperand = postfixOperand;
        this.operator = operator;
        this.prevValue = 0d;
        this.value = 0d;
    }

    public void setPrevValue(double prevValue) {
        this.prevValue = prevValue;
    }

    public void calculateResult() {
        Stack<String> stack = new Stack<>();
        String[] tokens = this.postfixOperand.split(" ");

        String instruction;
        double firstOperand, secondOperand;
        int it = 0;

        while (it < tokens.length && it > -1) {
            instruction = tokens[it++];

            if (this.isDouble(instruction)) {
                stack.push(instruction);
            } else {
                if (instruction.equals("-")) {
                    secondOperand = toDouble(stack.pop());

                    if (!stack.isEmpty()) {
                        firstOperand = toDouble(stack.pop());
                        stack.push("" + this.calculate(firstOperand, secondOperand, instruction.charAt(0)));
                    } else {
                        stack.push("" + (-secondOperand));
                    }
                } else {
                    secondOperand = toDouble(stack.pop());
                    firstOperand = toDouble(stack.pop());
                    stack.push("" + this.calculate(firstOperand, secondOperand, instruction.charAt(0)));
                }
            }
        }

        this.value = toDouble(stack.pop());
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

    public double getCurrentValue() {
        return this.value;
    }

    public String getOperand() {
        return this.origOperand;
    }

    public String getOperator() {
        return this.operator;
    }

    private boolean isDouble(String test) {
        try {
            double d = Double.parseDouble(test);
        } catch (NumberFormatException | NullPointerException err) {
            return false;
        }

        return true;
    }

    private double toDouble(String num) {
        return Double.parseDouble(num);
    }
}
