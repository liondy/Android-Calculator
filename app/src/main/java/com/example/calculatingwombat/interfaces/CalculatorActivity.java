package com.example.calculatingwombat.interfaces;

import com.example.calculatingwombat.model.Operand;

public interface CalculatorActivity {
    void addOperand(Operand newOperand);
    void showOperandDialog();
    void clearHistory();
}
