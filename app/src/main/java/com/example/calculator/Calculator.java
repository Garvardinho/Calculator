package com.example.calculator;

import java.util.Stack;

public class Calculator {
    private final Stack<Double> numbers;
    private final Stack<Operator> operators;

    public Calculator() {
        numbers = new Stack<>();
        operators = new Stack<>();
    }

    public void pushNumber(double num) {
        numbers.push(num);
    }

    public void pushOperator(Operator operator) throws ArithmeticException {
        executePreviousIsPossible(operator);
        operators.push(operator);
    }

    private void executePreviousIsPossible(Operator nextOperator) throws ArithmeticException {
        if (operators.size() == 0)
            return;

        Operator operatorToExecute = operators.peek();

        switch (operatorToExecute.getTitle()) {
            case "+":
            case "-":
            case "*":
            case "/": {
                if (numbers.size() < 2)
                    return;

                if (operatorToExecute.getPriority() < nextOperator.getPriority())
                    return;

                double rightOperand = numbers.pop();
                double leftOperand = numbers.pop();
                double result = execute(leftOperand, rightOperand, operatorToExecute);
                numbers.push(result);
                operators.pop();
            }
        }
    }

    private double execute(double left, double right, Operator operator) throws ArithmeticException {
        switch (operator.getTitle()) {
            case "+":
                return left + right;

            case "-":
                return left - right;

            case "*":
                return left * right;

            case "/":
                if (right == 0)
                    throw new ArithmeticException("Division by zero!");

                return left / right;

            default:
                return 0; // TODO: At least pointer
        }
    }

    public double compute() {
        executePreviousIsPossible(new Operator("="));
        return numbers.pop();
    }

    public void clearStacks() {
        numbers.clear();
        operators.clear();
    }
}
