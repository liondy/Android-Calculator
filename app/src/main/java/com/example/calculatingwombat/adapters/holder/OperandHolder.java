package com.example.calculatingwombat.adapters.holder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.OperandAdapter;
import com.example.calculatingwombat.interfaces.ItemTouchHelperViewHolder;
import com.example.calculatingwombat.model.Operand;

public class OperandHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
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
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.deleteButton.getId()) {
            this.opAdapter.handleGarbageButton(this.getAdapterPosition());
        }
    }

    public void setText(Operand operand) {
        this.operandResult.setText(operand.getFormattedValue());
        this.operandString.setText(Double.toString(operand.getCurrentValue()));
        this.operatorSymbol.setText("(" + operand.getOperator() + ")");
    }

    @Override
    public void onItemSelected() {
        itemView.setTranslationZ(15);
    }

    @Override
    public void onItemClear() {
        itemView.setTranslationZ(0);
    }
}
