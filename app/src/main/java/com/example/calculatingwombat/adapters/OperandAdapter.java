package com.example.calculatingwombat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.helper.ItemTouchHelperAdapter;
import com.example.calculatingwombat.adapters.holder.OperandHolder;
import com.example.calculatingwombat.fragments.MainFragment;
import com.example.calculatingwombat.model.Operand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperandAdapter extends RecyclerView.Adapter<OperandHolder> implements ItemTouchHelperAdapter  {
    private List<Operand> operandList;
    private MainFragment mainFragment;

    public OperandAdapter(MainFragment mf) {
        this.operandList = new ArrayList<>();
        this.mainFragment = mf;
    }

    public void syncData(List<Operand> operands) {
        Collections.copy(this.operandList, operands);
        this.notifyDataSetChanged();
    }

    public void addOperand(Operand operand) {
        this.operandList.add(operand);
        this.notifyItemInserted(this.operandList.size() - 1);
    }

    public void handleRemoveOperand(int idx) {
        this.mainFragment.removeOperand(idx);
    }

    public void removeOperand(int idx) {
        this.operandList.remove(idx);
        this.notifyItemRemoved(idx);
    }

    public void clear(){
        this.operandList.clear();
        this.notifyDataSetChanged();
    }

    public OperandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.operand_layout, parent, false);
        OperandHolder oh = new OperandHolder(view,this);
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
    public void onItemMove(int idx1, int idx2) {
        this.mainFragment.swapOperand(idx1, idx2);
    }

    @Override
    public void onItemSwipe(int idx) {
        this.mainFragment.removeOperand(idx);
    }
}
