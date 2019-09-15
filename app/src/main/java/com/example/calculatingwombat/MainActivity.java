package com.example.calculatingwombat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.example.calculatingwombat.fragments.HistoryFragment;
import com.example.calculatingwombat.fragments.MainFragment;
import com.example.calculatingwombat.fragments.OperandFragment;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.model.OperandResult;
import com.example.calculatingwombat.storage.Storage;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CalculatorActivity {
    FragmentManager fragmentManager;
    MainFragment mainFragment;

    DrawerLayout drawerLayout;

    Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setupToolbar();

        this.fragmentManager = this.getSupportFragmentManager();

        this.mainFragment = (MainFragment)this.fragmentManager.findFragmentById(R.id.main_fragment);

        NavigationView navigationView = this.findViewById(R.id.navigationMenu);
        navigationView.setNavigationItemSelectedListener(this);

        this.storage = new Storage(this);
    }

    @Override
    public void showOperandDialog() {
        String tag = this.getResources().getString(R.string.operand_fragment_label);
        OperandFragment operandFragment = OperandFragment.createOperandFragment();
        operandFragment.show(this.fragmentManager, tag);
    }

    @Override
    public void clearHistory() {
        this.storage.clearHistory();
    }

    @Override
    public void addOperand(Operand newOperand) {
        this.mainFragment.addOperand(newOperand);
    }

    private void setupToolbar() {
        Window window = this.getWindow();

        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        Toolbar toolbar = this.findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);

        this.drawerLayout = this.findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        this.drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        this.askBeforePausing();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.save_menu:
                this.handleSaveMenu();
                this.drawerLayout.closeDrawers();
                return true;
            case R.id.clear_menu:
                this.handleClearMenu();
                this.drawerLayout.closeDrawers();
                return true;
            case R.id.result_menu:
                this.handleResultMenu();
                this.drawerLayout.closeDrawers();
                return true;
            case R.id.history_menu:
                this.handleHistoryMenu();
                return true;
            case R.id.exit_menu:
                this.askBeforePausing();
                return true;
            default:
                return false;
        }
    }

    private void askBeforePausing() {
        if (!this.mainFragment.getSaveStatus()) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Save before quitting?");
            alertDialog.setMessage("It seems you haven't saved your results, would you like to save before quitting?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            handleSaveMenu();
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.show();
        } else {
            this.finish();
        }
    }

    private void handleSaveMenu() {
        List<Operand> operandList = this.mainFragment.getOperandList();

        this.storage.saveOperands(operandList);

        this.mainFragment.saveStatus();

        CharSequence text = "Calculation(s) saved";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    private void handleClearMenu() {
        this.mainFragment.clearOperand();
        this.storage.clearOperandList();
    }

    private void handleResultMenu() {
        OperandResult newResult = this.mainFragment.processResult();
        if (newResult != null) {
            this.storage.addNewHistory(newResult);
        }
    }

    private void handleHistoryMenu() {
        List<OperandResult> list = this.storage.getHistories();
        String tag = "History Fragment";
        HistoryFragment fragment = HistoryFragment.createHistoryFragment(list);
        fragment.show(this.fragmentManager, tag);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Operand> operandList = this.storage.getOperandList();
        List<OperandResult> operandResultList = this.storage.getHistories();

        if (operandList.size() > 0) {
            for (Operand operand: operandList) {
                this.mainFragment.addOperand(operand);
            }

            CharSequence text = "Calculation(s) loaded";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();

            this.mainFragment.saveStatus();
        }

        if (operandResultList.size() > 0) {
            this.mainFragment.setResultText(operandResultList.get(operandResultList.size() - 1));
        }
    }
}
