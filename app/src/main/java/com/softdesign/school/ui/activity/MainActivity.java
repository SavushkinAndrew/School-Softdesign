package com.softdesign.school.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.softdesign.school.Lg;
import com.softdesign.school.R;
import com.softdesign.school.ui.fragments.ContactsFragment;
import com.softdesign.school.ui.fragments.ProfileFragment;
import com.softdesign.school.ui.fragments.SettingsFraagment;
import com.softdesign.school.ui.fragments.TaskFragment;
import com.softdesign.school.ui.fragments.TeamFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getSimpleName();

    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private NavigationView mNavigationView;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lg.e(TAG, "OnCreate");


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        loadToolBar();
        setupDrawer();

        if (savedInstanceState != null) {

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ProfileFragment()).commit();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Lg.e(TAG, "OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Lg.e(TAG, "OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Lg.e(TAG, "OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Lg.e(TAG, "OnStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Lg.e(TAG, "OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Lg.e(TAG, "OnDestroy");
    }


    @Override
    public void onClick(View view) {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void loadToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(this.getClass().getSimpleName());
    }

    private void setupDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_profile:
                        mFragment = new ProfileFragment();
                        mNavigationView.getMenu().findItem(R.id.drawer_profile).setChecked(true);
                        break;
                    case R.id.drawer_contacts:
                        mFragment = new ContactsFragment();
                        mNavigationView.getMenu().findItem(R.id.drawer_contacts).setChecked(true);
                        break;
                    case R.id.drawer_team:
                        mFragment = new TeamFragment();
                        mNavigationView.getMenu().findItem(R.id.drawer_team).setChecked(true);
                        break;
                    case R.id.drawer_tasks:
                        mFragment = new TaskFragment();
                        mNavigationView.getMenu().findItem(R.id.drawer_tasks).setChecked(true);
                        break;
                    case R.id.drawer_setting:
                        mFragment = new SettingsFraagment();
                        mNavigationView.getMenu().findItem(R.id.drawer_setting).setChecked(true);
                        break;
                }

                if (mFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, mFragment)
                            .addToBackStack(null)
                            .commit();
                }

                mNavigationDrawer.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment findingFragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (findingFragment != null && findingFragment instanceof ProfileFragment) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        super.onBackPressed();
    }



}
