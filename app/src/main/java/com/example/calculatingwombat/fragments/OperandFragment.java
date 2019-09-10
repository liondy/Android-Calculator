package com.example.calculatingwombat.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

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
import com.example.calculatingwombat.model.utils.ShuntingYard;

import java.util.EmptyStackException;
import java.util.InputMismatchException;

public class OperandFragment extends DialogFragment implements View.OnClickListener {
    Spinner operatorList;
    EditText operand;
    Button okButton;
    Button cancelButton;

    CalculatorActivity activity;

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
        try {
            String orig = this.operand.getText().toString();
            String expression = this.convertToPostfix();
            String symbol = this.operatorList.getSelectedItem().toString();

            Operand newOperand = new Operand(symbol, orig, expression);

            try {
                newOperand.calculateResult();

                if (newOperand.getCurrentValue() == 0 && symbol.equals("/")) {
                    this.showDivideByZeroToast();
                } else {
                    this.activity.addOperand(newOperand);

                    this.dismiss();
                }
            } catch (ArithmeticException err) {
                this.showDivideByZeroToast();
            } catch (EmptyStackException err) {
                this.showErrorToast();
            }
        } catch (InputMismatchException err) {
            this.showErrorToast();
        }
    }

    private String convertToPostfix() {
        String expression = this.operand.getText().toString();

        expression = expression.replace("\\s+", "");

        ShuntingYard syParser = new ShuntingYard(expression);

        return syParser.evaluate();
    }

    private void showErrorToast() {
        Context context = this.getContext();
        String error = "Invalid operand, check your inputs";

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
