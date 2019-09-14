package com.example.calculatingwombat.model;

public class OperandResult {
    private double value;
    private String date;

    public OperandResult(double v, String d) {
        this.value = v;
        this.date = d;
    }

    public double getValue() {
        return this.value;
    }

    public String getDate() {
        return this.date;
    }

    public String getFormattedValue() {
        String format = "%.2f";
        return String.format(format, this.value);
    }
}
