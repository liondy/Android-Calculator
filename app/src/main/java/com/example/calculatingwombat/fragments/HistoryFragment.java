package com.example.calculatingwombat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.google.android.material.button.MaterialButton;

public class HistoryFragment extends DialogFragment implements View.OnClickListener {
    protected MaterialButton btn;
    protected CalculatorActivity activity;

    public HistoryFragment(){
        // empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container);
        this.btn = view.findViewById(R.id.history_btn);
        this.btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view){
        if(view.getId()==this.btn.getId()){
            this.closeDialog();
        }
    }

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
        return hf;
    }
}
