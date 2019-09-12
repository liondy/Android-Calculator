package com.example.calculatingwombat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.HistoryAdapter;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.OperandResult;
import com.google.android.material.button.MaterialButton;

public class HistoryFragment extends DialogFragment implements View.OnClickListener {
    protected MaterialButton btn;
    protected CalculatorActivity activity;
    protected HistoryAdapter histAdapter;
    protected RecyclerView resultList;

    public HistoryFragment(){
        // empty constructor
    }

    public void createAdapter(){
        this.histAdapter = new HistoryAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container);
        this.btn = view.findViewById(R.id.history_btn);
        this.resultList = view.findViewById(R.id.history_list);
        this.resultList.setAdapter(this.histAdapter);
        this.resultList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.btn.setOnClickListener(this);
        Log.i("test", "onCreateView: ");
        return view;
    }

    @Override
    public void onClick(View view){
        if(view.getId()==this.btn.getId()){
            this.closeDialog();
        }
    }

    public void addResult(OperandResult res){this.histAdapter.addResult(res);}

    public void closeDialog(){this.dismiss();}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CalculatorActivity) {
            this.activity = (CalculatorActivity)context;
        } else {
            throw new ClassCastException(context.toString() + " must implement CalculatorActivity.");
        }
    }

    public static HistoryFragment createHistoryFragment(){
        HistoryFragment hf = new HistoryFragment();
        hf.createAdapter();
        return hf;
    }
}
