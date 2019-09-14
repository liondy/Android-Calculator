package com.example.calculatingwombat.model;

public class Operand {
    private double prevValue;
    private char operator;
    private double operand;

    public Operand(String operator, String operand) {
        this.operand = Double.parseDouble(operand);
        this.operator = operator.charAt(0);
    }

    public void setPrevValue(double prevValue) {
        this.prevValue = prevValue;
    }

    /*
    @Deprecated
    public void calculateResult() {
        System.out.println(this.postfixOperand);
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
    */

    private double calculate(double first, double second, char operator) {
        switch (operator) {
            case '+': return first + second;
            case '-': return first - second;
            case '*': return first * second;
            case '/': return first / second;
            default: return Math.pow(first, second);
        }
    }

    public double getTotalValue() {
        return this.calculate(this.prevValue, this.operand, this.operator);
    }

    public double getCurrentValue() {
        return this.operand;
    }


    public char getOperator() {
        return this.operator;
    }

    public double getOperand(){
        return this.operand;
    }

    public String getFormattedValue() {
        String format = "%.2f";
        return String.format(format, this.getTotalValue());
    }
}
