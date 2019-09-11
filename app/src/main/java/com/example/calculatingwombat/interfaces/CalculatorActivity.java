package com.example.calculatingwombat.interfaces;

import com.example.calculatingwombat.model.Operand;

public interface CalculatorActivity {
    void addOperand(Operand newOperand);
    void addOperandToView(Operand newOperand);
    void swapOperand(int idx1, int idx2);
    void removeOperand(int idx);
    void showOperandDialog();
}
