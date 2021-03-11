package com.example.calculator;

public class Operator {
    private final String mTitle;
    private final byte mPriority;

    public Operator(String title) {
        mTitle = title;

        switch (title) {
            case "=": {
                mPriority = 0;
                break;
            }

            case "+":
            case "-": {
                mPriority = 1;
                break;
            }

            case "*":
            case "/": {
                mPriority = 2;
                break;
            }

            default:
                mPriority = 3;
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public byte getPriority() {
        return mPriority;
    }
}
