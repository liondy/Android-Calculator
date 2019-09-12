package com.example.calculatingwombat.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.HistoryAdapter;
import com.example.calculatingwombat.adapters.OperandAdapter;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.model.OperandResult;
import com.example.calculatingwombat.presenter.CalculatorPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton addButton;
    MaterialButton clear,result,history;
    RecyclerView operandList;
    TextView tv_res;
    OperandAdapter operandAdapter;
    CalculatorActivity listener;
    CalculatorPresenter presenter;
    HistoryFragment hf;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        this.tv_res = view.findViewById(R.id.calculator_result);
        this.operandList = view.findViewById(R.id.operand_list);

        this.operandAdapter = new OperandAdapter(this);

        this.hf = HistoryFragment.createHistoryFragment();

        this.operandList.setAdapter(this.operandAdapter);
        this.operandList.setLayoutManager(new LinearLayoutManager(getContext()));

        this.addButton = view.findViewById(R.id.add_button);
        this.clear = view.findViewById(R.id.clear);
        this.result = view.findViewById(R.id.result);
        this.history = view.findViewById(R.id.history);

        this.addButton.setOnClickListener(this);
        this.clear.setOnClickListener(this);
        this.result.setOnClickListener(this);
        this.history.setOnClickListener(this);

        return view;
    }

    public void setPresenter(CalculatorPresenter cp){this.presenter = cp;}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CalculatorActivity) {
            this.listener = (CalculatorActivity)context;
        } else {
            throw new ClassCastException(context.toString() + " must implement CalculatorActivity.");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.addButton.getId()) {
            this.listener.showOperandDialog();
        }
        else if(id == this.clear.getId()){
            this.clearOperand();
        }
        else if(id == this.result.getId()){
            this.addResult();
        }
        else{
            this.showHistoryDialog();
        }
    }

    public void addResult(){
        OperandResult result = this.presenter.addResult();

        try{
            this.tv_res.setText(Double.toString(result.getValue()));
        } catch(NullPointerException e){
            this.tv_res.setText("000.000");
        }
        if(result!=null) this.hf.addResult(result);
    }

    public void clearOperand(){
        this.presenter.clear();
        this.operandAdapter.clear();
    }

    public void showHistoryDialog(){
        FragmentManager fm = this.getFragmentManager();
        String tag = "History Fragment";
        this.hf.show(fm,tag);
    }

    public void removeOperand(int index) {
        this.presenter.deleteOperand(index);
    }

    public void addNewOperand(Operand newOperand) {
        this.operandAdapter.addOperand(newOperand);
    }
}
