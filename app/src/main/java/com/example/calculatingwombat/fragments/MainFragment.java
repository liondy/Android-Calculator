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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    CalculatorActivity listener;
    OperandAdapter adapter;
    OperandPresenter presenter;

    FloatingActionButton addButton;
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

        ItemTouchHelper.Callback cb = new OperandTouchHelper(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(cb);

        helper.attachToRecyclerView(this.operandList);

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

    public OperandResult processResult() {
        if (this.presenter.getOperands().size() == 0) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        Operand lastOperand = this.presenter.getOperands().get(this.presenter.getOperands().size() - 1);

        OperandResult result = new OperandResult(lastOperand.getTotalValue(),sdf.format(new Date()));

        this.setResultText(result);

        return result;
    }

    public void setResultText(OperandResult result) {
        this.tv_res.setText(result.getFormattedValue());
    }

    public void clearOperand() {
        this.presenter.clear();
        this.tv_res.setText("0.00");
        this.adapter.notifyDataSetChanged();
    }

    public void addOperand(Operand newOperand) {
        this.presenter.addOperand(newOperand);
        this.adapter.notifyDataSetChanged();
    }

    public List<Operand> getOperandList() {
        return this.presenter.getOperands();
    }

    public boolean getSaveStatus() {
        return this.presenter.getSave();
    }

    public void saveStatus() {
        this.presenter.setSave(true);
    }
}
