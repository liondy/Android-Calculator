package com.example.calculatingwombat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.example.calculatingwombat.fragments.MainFragment;
import com.example.calculatingwombat.fragments.OperandFragment;
import com.example.calculatingwombat.fragments.SettingsFragment;
import com.example.calculatingwombat.interfaces.CalculatorActivity;
import com.example.calculatingwombat.model.Operand;
import com.example.calculatingwombat.presenter.CalculatorPresenter;
import com.google.android.material.navigation.NavigationView;
import com.example.calculatingwombat.storage.CommaSettings;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CalculatorActivity {
    FragmentManager fragmentManager;
    MainFragment mainFragment;
    CalculatorPresenter calculatorPresenter;

    CommaSettings commaSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setupToolbar();

        this.fragmentManager = this.getSupportFragmentManager();

        this.mainFragment = (MainFragment)this.fragmentManager.findFragmentById(R.id.main_fragment);

        this.calculatorPresenter = new CalculatorPresenter(this);

        this.commaSettings = new CommaSettings(this);

        this.mainFragment.setPresenter(this.calculatorPresenter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationMenu);
        navigationView.setNavigationItemSelectedListener(this);
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
    public void changeSettings(int id) {

    }

    @Override
    public void addOperand(Operand newOperand) {
        this.calculatorPresenter.addOperands(newOperand);
    }

    @Override
    public void addOperandToView(Operand newOperand) {
        this.mainFragment.addNewOperand(newOperand);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.navigation_menu, menu);
//        Log.d("tag","lalala");
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.d("tag","lololo");
        switch (item.getItemId()) {
            case R.id.save_menu:
                Log.d("tag", "save");
                return true;
            case R.id.load_menu:
                Log.d("tag", "load");
                return true;
            case R.id.about_menu:
                Log.d("tag", "about");
                return true;
            case R.id.exit_menu:
                System.exit(0);
                Log.d("tag", "exit");
                return true;
            default:
                Log.d("tag", item.getItemId() + "");
                return false;
        }
    }



//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        Log.d("tag","lololo");
//        switch (item.getItemId()) {
//            case R.id.save_menu:
//                Log.d("tag", "save");
//                return true;
//            case R.id.load_menu:
//                Log.d("tag", "load");
//                return true;
//            case R.id.about_menu:
//                Log.d("tag", "about");
//                return true;
//            case R.id.exit_menu:
//                System.exit(0);
//                Log.d("tag", "exit");
//                return true;
//            default:
//                Log.d("tag", item.getItemId() + "");
//                return false;
//        }
//    }
}
