package com.example.calculatingwombat.presenter;

import com.example.calculatingwombat.model.Operand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperandPresenter {
    private List<Operand> operands;
    private boolean save;

    public OperandPresenter() {
        this.operands = new ArrayList<>();
        this.save = true;
    }

    public boolean getSave() {
        return this.save;
    }

    public void setSave(boolean saved) {
        this.save = saved;
    }

    public List<Operand> getOperands() {
        return this.operands;
    }

    public void addOperand(Operand newOperand) {
        if (this.operands.size() > 0) {
            double prev = this.operands.get(this.operands.size() - 1).getTotalValue();

            newOperand.setPrevValue(prev);
        }

        this.operands.add(newOperand);
        this.setSave(false);
    }

    public void deleteOperand(int idx) {
        this.operands.remove(idx);
        this.setPrevValues(idx, this.operands.size() - 1);
        this.setSave(false);
    }

    public void clear() {
        this.operands.clear();
        this.setSave(false);
    }

    public void swapOperand(int index1, int index2) {
        Collections.swap(this.operands, index1, index2);

        if (index2 < index1) {
            index1 += index2;
            index2 = index1 - index2;
            index1 -= index2;
        }

        this.setPrevValues(index1, index2);
        this.setSave(false);
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
