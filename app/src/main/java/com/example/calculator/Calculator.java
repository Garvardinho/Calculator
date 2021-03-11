package com.example.calculator;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Stack;

public class Calculator implements Parcelable {
    private final Stack<Double> mNumbers;
    private final Stack<Operator> mOperators;
    private final MainActivity mHandler;

    public Calculator(MainActivity handler) {
        mNumbers = new Stack<>();
        mOperators = new Stack<>();
        mHandler = handler;
    }

    protected Calculator(Parcel in) {
        mNumbers = (Stack<Double>) in.readValue(Stack.class.getClassLoader());
        mOperators = (Stack<Operator>) in.readValue(Stack.class.getClassLoader());
        mHandler = (MainActivity) in.readValue(MainActivity.class.getClassLoader());
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    public void pushNumber(double num) {
        mNumbers.push(num);
    }

    public void pushOperator(Operator operator) throws ArithmeticException {
        executePreviousIsPossible(operator);
        mOperators.push(operator);
    }

    private void executePreviousIsPossible(Operator nextOperator) throws ArithmeticException {
        if (mOperators.size() == 0)
            return;

        Operator operatorToExecute = mOperators.peek();

        switch (operatorToExecute.getTitle()) {
            case "+":
            case "-":
            case "*":
            case "/": {
                if (mNumbers.size() < 2)
                    return;

                if (operatorToExecute.getPriority() < nextOperator.getPriority())
                    return;

                double rightOperand = mNumbers.pop();
                double leftOperand = mNumbers.pop();
                double result = execute(leftOperand, rightOperand, operatorToExecute);
                mNumbers.push(result);
                mOperators.pop();
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
                if (right == 0) {
                    mHandler.showAlert("Division by zero!");
                    return 0;
                }

                return left / right;

            default:
                return 0; // TODO: At least pointer
        }
    }

    public double compute() {
        while (mOperators.size() != 0) {
            double rightOperand = mNumbers.pop();
            double leftOperand = mNumbers.pop();
            double result = execute(leftOperand, rightOperand, mOperators.pop());
            mNumbers.push(result);
        }

        return mNumbers.pop();
    }

    public void clearStacks() {
        mNumbers.clear();
        mOperators.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mNumbers);
        dest.writeValue(mOperators);
        dest.writeValue(mHandler);
    }
}
