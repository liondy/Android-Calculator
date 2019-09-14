package com.example.calculatingwombat.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.calculatingwombat.MainActivity;
import com.example.calculatingwombat.fragments.MainFragment;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.presenter.OperandPresenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Storage {
    public SharedPreferences sharedPreferences;
    protected List<Operand> arrOperand;
    protected OperandPresenter op;

    public Storage(Context context){
        this.sharedPreferences = context.getSharedPreferences("App_settings",0);
    }

    public void setPresenter(OperandPresenter op){
        this.op = op;
        this.arrOperand = op.getList();
    }

    public void saveList(){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt("size",this.arrOperand.size());
        int i;
        for (i=0;i<this.arrOperand.size();i++){
            editor.putString("operand"+i, String.valueOf(this.arrOperand.get(i).getOperand()));
            editor.putString("operator"+i,String.valueOf(this.arrOperand.get(i).getOperator()));
        }
        editor.apply();
    }

    public Operand[] loadList(){

        Operand[] newList = new Operand[this.sharedPreferences.getInt("size",0)];
        int i;
        for (i=0;i<newList.length;i++){
            Operand o = new Operand(this.sharedPreferences.getString("operator"+i,""),this.sharedPreferences.getString("operand"+i,""));
            newList[i]=o;
        }
        return newList;
    }

    public int getSize(){
        return this.arrOperand.size();
    }
}
