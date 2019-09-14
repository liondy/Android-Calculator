package com.example.calculatingwombat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.holder.ResultHolder;
import com.example.calculatingwombat.model.OperandResult;
import com.example.calculatingwombat.presenter.HistoryPresenter;

public class HistoryAdapter extends RecyclerView.Adapter<ResultHolder> {
    private HistoryPresenter presenter;

    public HistoryAdapter(HistoryPresenter presenter) {
        this.presenter = presenter;
    }

    public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_layout, parent, false);
        ResultHolder rh = new ResultHolder(view);

        return rh;
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        OperandResult target = this.presenter.getOperandResults().get(position);

        holder.setText(target);
    }

    @Override
    public int getItemCount() {
        return this.presenter.getOperandResults().size();
    }
}
