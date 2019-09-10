package com.example.calculatingwombat.adapters.holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.model.Operand;

public class OperandHolder extends RecyclerView.ViewHolder {
    private TextView operandResult;
    private TextView operandString;
    private Button deleteButton;

    public OperandHolder(View view) {
        super(view);

        this.operandResult = view.findViewById(R.id.operand_result);
        this.operandString = view.findViewById(R.id.operand_string);
        this.deleteButton = view.findViewById(R.id.operand_delete);
    }

    public void setText(Operand operand) {
        this.operandResult.setText(Double.toString(operand.getTotalValue()));
        this.operandString.setText(operand.getOperand());
    }
}
