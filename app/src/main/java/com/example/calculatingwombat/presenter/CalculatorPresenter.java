package com.example.calculatingwombat.presenter;

import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculatorPresenter {
    private List<Operand> operands;
    private CalculatorActivity activity;

    public CalculatorPresenter(CalculatorActivity activity) {
        this.activity = activity;
        this.operands = new ArrayList<>();
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
        this.activity.removeOperand(index);
    }

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
