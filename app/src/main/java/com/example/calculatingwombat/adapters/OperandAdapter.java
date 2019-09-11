package com.example.calculatingwombat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.helper.ItemTouchHelperAdapter;
import com.example.calculatingwombat.adapters.holder.OperandHolder;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperandAdapter extends RecyclerView.Adapter<OperandHolder> implements ItemTouchHelperAdapter  {
    private List<Operand> operandList;
    private CalculatorActivity activity;

    public OperandAdapter(CalculatorActivity activity) {
        this.operandList = new ArrayList<>();
        this.activity = activity;
    }

    public void addOperand(Operand operand) {
        this.operandList.add(operand);
        this.notifyDataSetChanged();
    }

    public void removeOperand(int idx) {
        this.operandList.remove(idx);
        this.notifyDataSetChanged();
    }

    public OperandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.operand_layout, parent, false);

        OperandHolder oh = new OperandHolder(view);

        return oh;
    }

    @Override
    public void onBindViewHolder(OperandHolder holder, int position) {
        Operand target = this.operandList.get(position);

        holder.setText(target);
    }

    @Override
    public int getItemCount() {
        return this.operandList.size();
    }

    @Override
    public void onItemMove(int from, int to) {
        Collections.swap(this.operandList, from, to);

        this.activity.swapOperand(from, to);

        this.
    }
}
