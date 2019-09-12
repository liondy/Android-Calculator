package com.example.calculatingwombat.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.calculatingwombat.R;
import com.example.calculatingwombat.interfaces.CalculatorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends DialogFragment implements View.OnClickListener {
    Spinner commaList;
    CalculatorActivity activity;

    Button applyButton;
    Button cancelButton;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        this.commaList = view.findViewById(R.id.comma_limit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.comma_array, R.layout.spinner_layout);

        adapter.setDropDownViewResource(R.layout.spinner_layout);

        this.applyButton = view.findViewById(R.id.apply_changes_button);
        this.cancelButton = view.findViewById(R.id.cancel_changes_button);

        return view;
    }

    private void dismissFragment() {
        this.dismiss();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.cancelButton.getId()) {
            this.dismissFragment();
        } else {
            int idx = this.commaList.getSelectedItemPosition();

            //this.activity.changeSettings(idx);
        }
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

    public void setSettings(int id) {
        this.commaList.setSelection(id);
    }

    public static SettingsFragment createSettingsFragment() {
        SettingsFragment fragment = new SettingsFragment();

        return fragment;
    }
}
