package com.example.calculatingwombat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OperandResult implements Parcelable {
    private double value;
    private String date;

    public OperandResult(double v, String d) {
        this.value = v;
        this.date = d;
    }

    private OperandResult(Parcel in) {
       this.value = in.readDouble();
       this.date = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(this.value);
        out.writeString(this.date);
    }

    public static final Parcelable.Creator<OperandResult> CREATOR
            = new Parcelable.Creator<OperandResult>() {
        public OperandResult createFromParcel(Parcel in) {
            return new OperandResult(in);
        }

        public OperandResult[] newArray(int size) {
            return new OperandResult[size];
        }
    };


}
