package com.example.calculator;

public class Operator {
    private final String title;
    private final byte priority;

    public Operator(String title) {
        this.title = title;
        switch (title) {
            case "=": {
                priority = 0;
                break;
            }

            case "+":
            case "-": {
                priority = 1;
                break;
            }

            case "*":
            case "/": {
                priority = 2;
                break;
            }

            default:
                priority = 3;
        }
    }

    public String getTitle() {
        return title;
    }

    public byte getPriority() {
        return priority;
    }
}
