package com.example.calculatingwombat.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.adapters.OperandAdapter;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton addButton;
    RecyclerView operandList;

    OperandAdapter operandAdapter;

    CalculatorActivity listener;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        this.operandList = view.findViewById(R.id.operand_list);

        this.operandAdapter = new OperandAdapter();

        this.operandList.setAdapter(this.operandAdapter);
        this.operandList.setLayoutManager(new LinearLayoutManager(getContext()));

        this.addButton = view.findViewById(R.id.add_button);
        this.addButton.setOnClickListener(this);

        if (this.operandList == null) {
            Log.d("operandList", null);
        }

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
        }
    }


    public void addNewOperand(Operand newOperand) {
        System.out.println(this.listener.toString());
        System.out.println(this.operandAdapter.toString());
        this.operandAdapter.addOperand(newOperand);
    }

    public static MainFragment createMainFragment() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }
}
