package com.example.calculatingwombat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.calculatingwombat.fragments.MainFragment;
import com.example.calculatingwombat.fragments.OperandFragment;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.presenter.CalculatorPresenter;

public class MainActivity extends AppCompatActivity implements CalculatorActivity {
    FragmentManager fragmentManager;
    MainFragment mainFragment;
    CalculatorPresenter calculatorPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setupToolbar();

        this.fragmentManager = this.getSupportFragmentManager();

        this.mainFragment = (MainFragment) this.fragmentManager.findFragmentById(R.id.main_fragment);

        this.calculatorPresenter = new CalculatorPresenter(this);
    }

    @Override
    public void showOperandDialog() {
        String tag = this.getResources().getString(R.string.operand_fragment_label);
        OperandFragment operandFragment = OperandFragment.createOperandFragment();
        operandFragment.show(this.fragmentManager, tag);
    }

    @Override
    public void addOperand(Operand newOperand) {
        this.calculatorPresenter.addOperands(newOperand);
    }

    @Override
    public void addOperandToView(Operand newOperand) {
        this.mainFragment.addNewOperand(newOperand);
    }

    @Override
    public void removeOperand(int index) {

    }

    @Override
    public void swapOperand(int idx1, int idx2) {

    }

    private void setupToolbar() {
        Window window = this.getWindow();

        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        Toolbar toolbar = this.findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = this.findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}
