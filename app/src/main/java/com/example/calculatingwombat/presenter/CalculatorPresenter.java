package com.example.calculatingwombat.presenter;

import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;

import java.util.ArrayList;
import java.util.List;

public class CalculatorPresenter {
    private List<Operand> operands;
    private CalculatorActivity activity;

    public CalculatorPresenter(CalculatorActivity activity) {
        this.activity = activity;
        this.operands = new ArrayList<>();
    }

    public void addOperands(Operand newOperand) {
        this.operands.add(newOperand);
        this.activity.addOperandToView(newOperand);
    }

    public void deleteOperand(int index) {
        this.operands.remove(index);
        this.activity.removeOperand(index);
    }

    public void swapOperand(int index1, int index2) {

    }
}
