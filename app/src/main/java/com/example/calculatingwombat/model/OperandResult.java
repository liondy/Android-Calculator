package com.example.calculatingwombat.model;

public class OperandResult {
    private double value;
    private String date;

    public OperandResult(double v, String d){
        this.value = v;
        this.date = d;
    }

    public double getValue(){return this.value;}
    public String getDate(){return this.date;}
    public void setvalue(double inp){this.value = inp;}
    public void setDate(String inp){this.date = inp;}
}
