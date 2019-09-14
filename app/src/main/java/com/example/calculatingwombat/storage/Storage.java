package com.example.calculatingwombat.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.calculatingwombat.MainActivity;
import com.example.calculatingwombat.fragments.MainFragment;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.model.OperandResult;
import com.example.calculatingwombat.presenter.OperandPresenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Storage {
    protected SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "CALCULATING_WOMBAT";

    public Storage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,0);
    }

    public void addNewHistory(OperandResult result) {
        int size = this.sharedPreferences.getInt("history_size", 0);

        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        editor.putString("history_value" + size, Double.toString(result.getValue()));
        editor.putString("history_date" + size, result.getDate());

        editor.putInt("history_size", size + 1);

        editor.commit();
    }

    public List<OperandResult> getHistories() {
        List<OperandResult> list = new ArrayList<>();
        int size = this.sharedPreferences.getInt("history_size", 0);

        for (int i = 0; i < size; i++) {
            String value = this.sharedPreferences.getString("history_value" + i, "");
            String date = this.sharedPreferences.getString("history_date" + i, "");

            OperandResult result = new OperandResult(Double.parseDouble(value), date);
            list.add(result);
        }

        return list;
    }

    public void clearHistory() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        int history_size = this.sharedPreferences.getInt("history_size", 0);

        for (int i = 0; i < history_size; i++) {
            editor.remove("history_value" + i);
            editor.remove("history_date" + i);
        }

        editor.remove("history_size");

        editor.commit();
    }

    public void clearOperandList() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        int size = this.sharedPreferences.getInt("operand_size", 0);

        for (int i = 0; i < size; i++) {
            String operandId = "operand_" + i;
            String operatorId = "operator_" + i;

            editor.remove(operandId);
            editor.remove(operatorId);
        }

        editor.remove("operand_size");

        editor.commit();
    }

    public void saveOperands(List<Operand> list) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        int size = list.size();
        editor.putInt("operand_size", size);

        for (int i = 0; i < size; i++) {
            String operandId = "operand_" + i;
            String operatorId = "operator_" + i;

            editor.putString(operandId, String.valueOf(list.get(i).getOperand()));
            editor.putString(operatorId,String.valueOf(list.get(i).getOperator()));
        }

        editor.commit();
    }

    public List<Operand> getOperandList() {
        List<Operand> list = new ArrayList<>();
        int size = this.sharedPreferences.getInt("operand_size", 0);

        for (int i = 0; i < size; i++) {
            String operator = this.sharedPreferences.getString("operator_" + i,"");
            String operand = this.sharedPreferences.getString("operand_" + i,"");
            Operand newOperand = new Operand(operator, operand);
            list.add(newOperand);
        }

        return list;
    }
}
