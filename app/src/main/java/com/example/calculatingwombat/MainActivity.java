package com.example.calculatingwombat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.example.calculatingwombat.fragments.MainFragment;
import com.example.calculatingwombat.fragments.OperandFragment;
import com.example.calculatingwombat.fragments.SettingsFragment;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.presenter.OperandPresenter;
import com.example.calculatingwombat.storage.Storage;
import com.google.android.material.navigation.NavigationView;
import com.example.calculatingwombat.storage.CommaSettings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CalculatorActivity {
    FragmentManager fragmentManager;
    MainFragment mainFragment;

    CommaSettings commaSettings;
    Storage s;

    boolean save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setupToolbar();

        this.fragmentManager = this.getSupportFragmentManager();

        this.mainFragment = (MainFragment)this.fragmentManager.findFragmentById(R.id.main_fragment);

        this.commaSettings = new CommaSettings(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationMenu);
        navigationView.setNavigationItemSelectedListener(this);

        this.s = new Storage(this);

        this.save = false;
    }

    @Override
    public void showOperandDialog() {
        String tag = this.getResources().getString(R.string.operand_fragment_label);
        OperandFragment operandFragment = OperandFragment.createOperandFragment();
        operandFragment.show(this.fragmentManager, tag);
    }


    public void showSettingsDialog() {
        String tag = this.getResources().getString(R.string.settings_fragment_label);
        SettingsFragment settingsFragment = SettingsFragment.createSettingsFragment();
        settingsFragment.show(this.fragmentManager, tag);
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

        DrawerLayout drawerLayout = this.findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
    }

    @Override
    public void onBackPressed(){
        closeProgram();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.save_menu:
                save();
                return true;
            case R.id.load_menu:
                load();
                return true;
            case R.id.settings:
                Log.d("tag","settings");
                return true;
            case R.id.exit_menu:
                closeProgram();
                return true;
            default:
                return false;
        }
    }

    private void save(){
        super.onPause();
        this.s.setPresenter(mainFragment.getPresenter());
        this.s.sharedPreferences.edit().remove("operand").apply();
        this.s.sharedPreferences.edit().remove("operator").apply();
        this.s.sharedPreferences.edit().apply();
        this.s.saveList();
        Toast.makeText(this,"Calculation Saved",Toast.LENGTH_LONG).show();
        this.save=true;
    }

    private void load(){
        super.onResume();
        Operand[] list = this.s.loadList();
        int i;
        for (i=0;i<list.length;i++){
            this.mainFragment.addOperand(list[i]);
        }
    }

    private void closeProgram(){
        if(!save){
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you want to save your calculation?");
            builder.setCancelable(true);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    moveTaskToBack(true);
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    save();
                    finish();
                    moveTaskToBack(true);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else{
            finish();
            moveTaskToBack(true);
        }
    }
}
