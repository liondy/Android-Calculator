package com.example.calculatingwombat.adapters.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.HistoryAdapter;
import com.example.calculatingwombat.model.OperandResult;

public class ResultHolder extends RecyclerView.ViewHolder {
    private TextView result;
    private TextView date;

    public ResultHolder(View view){
        super(view);
        this.result = view.findViewById(R.id.history_result);
        this.date = view.findViewById(R.id.history_date);
    }

    public void setText(OperandResult op){
        this.result.setText(op.getFormattedValue());
        this.date.setText(op.getDate());
    }
}
