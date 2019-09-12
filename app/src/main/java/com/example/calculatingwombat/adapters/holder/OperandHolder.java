package com.example.calculatingwombat.adapters.holder;

import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.OperandAdapter;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;

public class OperandHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView operandResult;
    private TextView operandString;
    private TextView operatorSymbol;
    private ImageButton deleteButton;
    private OperandAdapter opAdapter;

    public OperandHolder(View view, OperandAdapter oa) {
        super(view);
        this.opAdapter = oa;
        this.operandResult = view.findViewById(R.id.operand_result);
        this.operandString = view.findViewById(R.id.operand_string);
        this.operatorSymbol = view.findViewById(R.id.operator_symbol);
        this.deleteButton = view.findViewById(R.id.operand_delete);
        this.deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Log.i("op_btn_click: ", "debug "+this.getAdapterPosition());
        this.opAdapter.removeOperand(this.getAdapterPosition());
    }

    public void setText(Operand operand) {
        this.operandResult.setText(Double.toString(operand.getTotalValue()));
        this.operandString.setText(operand.getOperand());
        this.operatorSymbol.setText("(" + operand.getOperator() + ")");
    }
}
