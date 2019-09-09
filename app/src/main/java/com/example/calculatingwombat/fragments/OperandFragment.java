package com.example.calculatingwombat.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;

import java.util.regex.Pattern;

public class OperandFragment extends DialogFragment implements View.OnClickListener {
    Spinner operatorList;
    TextView operand;
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
                R.array.operator_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
        boolean valid = this.checkOperandValidity();

        if (valid) {
            String expression = this.operand.getText().toString();

            Operand newOperand = new Operand(this.operatorList.getSelectedItem().toString(), expression);

            try {
                newOperand.calculate();

                this.activity.addOperand(newOperand);

                this.dismiss();
            } catch (ArithmeticException err) {
                this.showDivideByZeroToast();
            }
        } else {
            this.showErrorToast();
        }
    }

    private boolean checkOperandValidity() {
        String expression = this.operand.getText().toString();

        expression = expression.replace(" +", "");

        if (Pattern.matches("[^0-9+-/*^()]", expression) || expression.isEmpty()) {
            return false;
        } else {
            boolean number = true;
            int it = 0;
            int parentheses = 0;
            char last = expression.charAt(it);
            int len = expression.length();
            while (it < len) {
                char eval = expression.charAt(it);
                if (eval == '(') {
                    if (!number) {
                        return false;
                    } else
                        parentheses++;
                    it++;
                } else if (eval == ')') {
                    if(parentheses == 0 || !this.isNumber(last)) {
                        return false;
                    }
                    else parentheses--;
                    it++;
                } else {
                    if (this.isNumber(eval)) {
                        if(!number) {
                            return false;
                        } else {
                            it = this.getNumberLimit(expression, it);
                            number = false;
                        }
                    } else {
                        if (number) {
                            return false;
                        } else {
                            it++;
                            number = true;
                        }
                    }

                    last = eval;
                }
            }

            if (!this.isNumber(last) || parentheses != 0) {
                return false;
            }

            return true;
        }
    }

    private int getNumberLimit(String expression, int idx) {
        int end = idx + 1;
        int len = expression.length();
        while(end < len && this.isNumber(expression.charAt(end)))
            end++;

        return end;
    }

    private boolean isNumber(char num) {
        return num >= 48 && num <= 57;
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
            throw new ClassCastException("HAHAHAH");
        }
    }

    public static OperandFragment createOperandFragment() {
        OperandFragment fragment = new OperandFragment();

        return fragment;
    }
}
