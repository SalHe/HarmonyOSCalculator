package com.example.calculator.eval;

public class SimpleEvaluation {

    private StringBuilder expressionBuilder;

    private SimpleEvaluation() {
        expressionBuilder = new StringBuilder();
    }

    public void append(String s) {
        if (".".equals(s) && expressionBuilder.charAt(expressionBuilder.length() - 1) == '.')
            return;
        expressionBuilder.append(s);
    }

    public void backspace() {
        if (expressionBuilder.length() > 0)
            expressionBuilder.deleteCharAt(expressionBuilder.length() - 1);
    }

    public double eval() {

        return 0;
    }

    public String getExpression() {
        return expressionBuilder.toString();
    }

    public static SimpleEvaluation getInstance() {
        return new SimpleEvaluation();
    }

}
