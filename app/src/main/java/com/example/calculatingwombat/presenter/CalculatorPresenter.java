package com.example.calculatingwombat.presenter;

import android.util.Log;

import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.model.OperandResult;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;

public class CalculatorPresenter {
    private List<Operand> operands;
    private List<OperandResult> operandRes;
    private CalculatorActivity activity;

    public CalculatorPresenter(CalculatorActivity activity) {
        this.activity = activity;
        this.operands = new ArrayList<>();
        this.operandRes = new ArrayList<>();
    }

    public int getSize() {
        return this.operands.size();
    }

    public void addOperands(Operand newOperand) {
        if (this.operands.size() > 0) {
            double prev = this.operands.get(this.operands.size() - 1).getTotalValue();

            newOperand.setPrevValue(prev);
        }

        this.operands.add(newOperand);
        this.activity.addOperandToView(newOperand);
    }

    public void deleteOperand(int index) {
        this.operands.remove(index);
        this.setPrevValues(index, this.operands.size() - 1);
    }

    public OperandResult addResult(){
        OperandResult res = null;
        if(this.operands.size()!=0){
            Operand temp = this.operands.get(this.operands.size()-1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            Log.i("date: ", sdf.format(new Date()));
            res = new OperandResult(temp.getTotalValue(),sdf.format(new Date()));
            this.operandRes.add(res);
        }
        return res;
    }

    public void clear(){this.operands.clear();}

    public void swapOperand(int index1, int index2) {
        Collections.swap(this.operands, index1, index2);

        if (index2 < index1) {
            index1 += index2;
            index2 = index1 - index2;
            index1 -= index2;
        }

        this.setPrevValues(index1, index2);
    }

    private void setPrevValues(int from, int to) {
        for (; from <= to; from++) {
            Operand operand = this.operands.get(from);

            if (from == 0) {
                operand.setPrevValue(0);
            } else {
                operand.setPrevValue(this.operands.get(from - 1).getTotalValue());
            }
        }
    }
}
