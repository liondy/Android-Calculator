package com.example.calculatingwombat.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.OperandAdapter;
import com.example.calculatingwombat.adapters.helper.OperandTouchHelper;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.model.OperandResult;
import com.example.calculatingwombat.presenter.OperandPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    CalculatorActivity listener;
    OperandAdapter adapter;
    OperandPresenter presenter;

    FloatingActionButton addButton;
    MaterialButton clearButton, resultButton;
    RecyclerView operandList;
    TextView tv_res;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        this.tv_res = view.findViewById(R.id.calculator_result);
        this.operandList = view.findViewById(R.id.operand_list);

        this.presenter = new OperandPresenter();
        this.adapter = new OperandAdapter(this.presenter);
        this.adapter.setHasStableIds(true);

        this.operandList.setAdapter(adapter);
        this.operandList.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        });

        this.operandList.setItemAnimator(new SlideInUpAnimator());

        this.addButton = view.findViewById(R.id.add_button);
        this.clearButton = view.findViewById(R.id.clear_button);
        this.resultButton = view.findViewById(R.id.result_button);

        ItemTouchHelper.Callback cb = new OperandTouchHelper(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(cb);

        helper.attachToRecyclerView(this.operandList);

        this.addButton.setOnClickListener(this);
        this.clearButton.setOnClickListener(this);
        this.resultButton.setOnClickListener(this);

        return view;
    }

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
        } else if (id == this.clearButton.getId()){
            this.clearOperand();
        } else if (id == this.resultButton.getId()){
            this.addResult();
        }
    }

    public void addResult() {
        OperandResult result = this.presenter.addResult();

        try {
            this.tv_res.setText(Double.toString(result.getValue()));
        } catch (NullPointerException e){
            this.tv_res.setText("000.000");
        }
    }

    public void clearOperand() {
        this.presenter.clear();
        this.adapter.notifyDataSetChanged();
    }

    public void addOperand(Operand newOperand) {
        this.presenter.addOperand(newOperand);
        this.adapter.notifyDataSetChanged();
    }
}
