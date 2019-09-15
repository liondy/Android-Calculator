package com.example.calculatingwombat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.HistoryAdapter;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.OperandResult;
import com.example.calculatingwombat.presenter.HistoryPresenter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends DialogFragment implements View.OnClickListener {
    protected MaterialButton okButton;
    protected MaterialButton clearButton;
    protected RecyclerView resultList;

    protected CalculatorActivity activity;

    protected HistoryAdapter adapter;
    protected HistoryPresenter presenter;

    public HistoryFragment() {
        // empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container);

        this.okButton = view.findViewById(R.id.history_btn);
        this.clearButton = view.findViewById(R.id.clear_history_btn);
        this.resultList = view.findViewById(R.id.history_list);

        this.presenter = new HistoryPresenter();
        this.adapter = new HistoryAdapter(this.presenter);
        this.resultList.setAdapter(adapter);
        this.resultList.setLayoutManager(new LinearLayoutManager(getContext()));

        List<OperandResult> list = this.getArguments().getParcelableArrayList("histories");

        for (OperandResult result: list) {
            this.addOperandResult(result);
        }

        this.okButton.setOnClickListener(this);
        this.clearButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){
        int id = view.getId();

        if (id == this.okButton.getId()){
            this.closeDialog();
        } else if (id == this.clearButton.getId()) {
            this.clearHistory();
            this.activity.clearHistory();
        }
    }

    public void closeDialog() {
        this.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CalculatorActivity) {
            this.activity = (CalculatorActivity)context;
        } else {
            throw new ClassCastException(context.toString() + " must implement CalculatorActivity.");
        }
    }

    private void addOperandResult(OperandResult result) {
        this.presenter.addOperandResult(result);
        this.adapter.notifyDataSetChanged();
    }

    private void clearHistory() {
        this.presenter.clearOperandResult();
        this.adapter.notifyDataSetChanged();
    }

    public static HistoryFragment createHistoryFragment(List<OperandResult> list) {
        HistoryFragment hf = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("histories", (ArrayList<OperandResult>)list);
        hf.setArguments(bundle);
        return hf;
    }
}
