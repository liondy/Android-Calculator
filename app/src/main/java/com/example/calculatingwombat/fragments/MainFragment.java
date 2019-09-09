package com.example.calculatingwombat.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton addButton;
    RecyclerView operandList;

    CalculatorActivity listener;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container);

        this.addButton = view.findViewById(R.id.add_button);
        this.operandList = view.findViewById(R.id.operand_list);

        this.addButton.setOnClickListener(this);

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

    public void setViewAdapter(RecyclerView.Adapter adapter) {
        this.operandList.setAdapter(adapter);
    }

    public static MainFragment createMainFragment() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }
}
