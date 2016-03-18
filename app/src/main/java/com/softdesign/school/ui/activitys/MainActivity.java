package com.softdesign.school.ui.activitys;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;

import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.softdesign.school.R;

import com.softdesign.school.ui.fragments.ContactsFragment;
import com.softdesign.school.ui.fragments.ProfileFragment;
import com.softdesign.school.ui.fragments.SettingsFragment;
import com.softdesign.school.ui.fragments.TaskFragment;
import com.softdesign.school.ui.fragments.TeamFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private Fragment mFragment;
    private AppBarLayout.LayoutParams params = null;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigation_drawer)
    DrawerLayout mNavigationDrawer;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.main_fab)
    FloatingActionButton mFab;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupDrawer();
        loadToolBar();

        mAppBarLayout.setExpanded(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ProfileFragment()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);

    }

    private void loadToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        params = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_profile:
                        mFragment = new ProfileFragment();
                        break;
                    case R.id.drawer_contacts:
                        mFragment = new ContactsFragment();
                        break;
                    case R.id.drawer_team:
                        mFragment = new TeamFragment();
                        break;
                    case R.id.drawer_tasks:
                        mFragment = new TaskFragment();
                        break;
                    case R.id.drawer_setting:
                        mFragment = new SettingsFragment();
                        break;
                }

                if (mFragment != null) {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, mFragment)
                            .addToBackStack("a")
                            .commit();
                }

                mNavigationDrawer.closeDrawers();
                return false;
            }
        });
    }

    public void selectedItem(int resource, String title) {
        mNavigationView.getMenu().findItem(resource).setChecked(true);
        mCollapsingToolbar.setTitle(title);
    }

    /**
     * Сворачивает ToolBar
     *
     * @param collapse true - свернуть / false -  развернуть
     */
    public void collapseAppBar(boolean collapse) {
        if (collapse) {
            final Handler handler = new Handler();
            mAppBarLayout.setExpanded(false);
            handler.postDelayed(new Runnable() { //выполнить функцию через 600 секунд
                @Override
                public void run() {
                    LockToolBar();
                }
            }, 300);

        } else {
            UnLockToolBar();
            mAppBarLayout.setExpanded(true);
        }
    }

    /**
     * Снимает блокировку с ToolBar выставляя scrollFlag
     */
    private void LockToolBar() {
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(params);

    }

    /**
     * Блокирует ToolBar выставляя scrollFlag
     */
    private void UnLockToolBar() {
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(params);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment findingFragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (findingFragment != null && findingFragment instanceof ProfileFragment) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


}
