package com.example.calculatingwombat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.interfaces.ItemTouchHelperAdapter;
import com.example.calculatingwombat.adapters.holder.OperandHolder;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.presenter.OperandPresenter;

public class OperandAdapter extends RecyclerView.Adapter<OperandHolder> implements ItemTouchHelperAdapter  {
    private OperandPresenter presenter;

    public OperandAdapter(OperandPresenter presenter) {
        this.presenter = presenter;
    }

    public OperandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.operand_layout, parent, false);
        OperandHolder oh = new OperandHolder(view,this);

        return oh;
    }

    @Override
    public void onBindViewHolder(OperandHolder holder, int position) {
        Operand target = this.presenter.getOperands().get(position);

        holder.setText(target);
    }

    @Override
    public int getItemCount() {
        return this.presenter.getOperands().size();
    }

    @Override
    public long getItemId(int position) {
        return this.presenter.getOperands().get(position).hashCode();
    }

    @Override
    public void onItemMove(int idx1, int idx2) {
        this.presenter.swapOperand(idx1, idx2);
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemSwipe(int idx) {
        this.presenter.deleteOperand(idx);
        this.notifyDataSetChanged();
    }

    public void handleGarbageButton(int idx) {
        this.presenter.deleteOperand(idx);
        this.notifyDataSetChanged();
    }
}
