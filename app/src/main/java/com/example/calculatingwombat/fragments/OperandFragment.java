package com.example.calculatingwombat.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;

public class OperandFragment extends DialogFragment implements View.OnClickListener {
    Spinner operatorList;
    EditText operand;
    Button okButton;
    Button cancelButton;
    CalculatorActivity activity;

    boolean err;

    public OperandFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container);

        this.operatorList = view.findViewById(R.id.operator_list);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.operator_array, R.layout.spinner_layout);

        adapter.setDropDownViewResource(R.layout.spinner_layout);

        this.operatorList.setAdapter(adapter);

        this.operand = view.findViewById(R.id.operand);
        this.okButton = view.findViewById(R.id.ok_button);
        this.cancelButton = view.findViewById(R.id.cancel_button);

        this.okButton.setOnClickListener(this);
        this.cancelButton.setOnClickListener(this);

        this.err = false;

        this.operand.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (err) {
                    err = false;
                    changeOperandTint();
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.okButton.getId()) {
            this.handleOKButton();
        } else if (id == this.cancelButton.getId()) {
            this.handleCancelButton();
        }
    }

    private void handleCancelButton() {
        this.dismiss();
    }

    private void handleOKButton() {
        String operand = this.operand.getText().toString();
        String operator = this.operatorList.getSelectedItem().toString();

        if (operand.isEmpty()) {
            this.showEmptyToast();
            this.err = true;
            this.changeOperandTint();
        } else if (operator.equals("/") && Double.parseDouble(operand) == 0) {
            this.showDivideByZeroToast();
            this.err = true;
            this.changeOperandTint();
        } else {
            Operand newOperand = new Operand(operator, operand);
            this.activity.addOperand(newOperand);

            this.dismiss();
        }
    }

    private void changeOperandTint() {
        if (this.err) {
            this.operand.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.colorError)));
        } else {
            this.operand.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.colorPrimary)));
        }
    }

    private void showEmptyToast() {
        Context context = this.getContext();
        String error = "Inputs must not be empty";

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, error, duration);
        toast.show();
    }

    private void showDivideByZeroToast() {
        Context context = this.getContext();
        String error = "Divide by zero detected, check your inputs";

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, error, duration);
        toast.show();
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

    public static OperandFragment createOperandFragment() {
        OperandFragment fragment = new OperandFragment();

        return fragment;
    }
}
