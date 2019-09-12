package com.example.calculatingwombat.interfaces;

import com.example.calculatingwombat.model.Operand;

public interface CalculatorActivity {
    void addOperand(Operand newOperand);
    void addOperandToView(Operand newOperand);
    void showSettingsDialog();
    void showOperandDialog();
}
