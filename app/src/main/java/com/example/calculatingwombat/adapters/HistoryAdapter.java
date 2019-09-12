package com.example.calculatingwombat.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.holder.OperandHolder;
import com.example.calculatingwombat.adapters.holder.ResultHolder;
import com.example.calculatingwombat.fragments.HistoryFragment;
import com.example.calculatingwombat.fragments.MainFragment;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.OperandResult;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<ResultHolder> {
    private List<OperandResult> resultHist;
    private HistoryFragment hf;

    public HistoryAdapter(HistoryFragment hf){
        this.hf= hf;
        this.resultHist = new ArrayList<>();
    }

    public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_layout, parent, false);
        ResultHolder rh = new ResultHolder(view);
        return rh;
    }

    public void addResult(OperandResult or){
        Log.i("masuk", "addResult: ");
        this.resultHist.add(or);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        OperandResult target = this.resultHist.get(position);
        holder.setText(target);
        Log.i("test", "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return this.resultHist.size();
    }
}
