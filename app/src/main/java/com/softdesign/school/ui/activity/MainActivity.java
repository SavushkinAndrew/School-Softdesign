package com.softdesign.school.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getSimpleName();
    private static final String VISIBLE_KEY ="visable";
    private static final String COUNT_KEY ="count";
    private static final String TOOLBAR_COLOR = "toolbar";
    private static final String STATUS_BAR_COLOR = "statusbar";

    private EditText mEditText;
    private CheckBox mCheckBox;
    private Toolbar mToolbar;
    private Toast mToast;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private int mCount;
    private int mToolbarColor;
    private int mStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lg.e(TAG, "OnCreate");



        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        loadToolBar();

        mEditText = (EditText)findViewById(R.id.text_field_1);
        mCheckBox = (CheckBox)findViewById(R.id.checkBox);

        mButton1 =(Button)findViewById(R.id.button1);
        mButton2 =(Button)findViewById(R.id.button2);
        mButton3 =(Button)findViewById(R.id.button3);

        mCheckBox.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);

        if (savedInstanceState != null) {
            int visibleState = savedInstanceState.getBoolean(VISIBLE_KEY) ? View.VISIBLE : View.INVISIBLE;
            mCount = savedInstanceState.getInt(COUNT_KEY);
            mCount++;

            mToolbarColor = savedInstanceState.getInt(TOOLBAR_COLOR);
            mStatusBar = savedInstanceState.getInt(STATUS_BAR_COLOR);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(mToolbarColor);
            }
            mToolbar.setBackgroundColor(mStatusBar);
            mToast = Toast.makeText(this, "Aктивити создано " + mCount + " раз", Toast.LENGTH_SHORT);
        } else {
            mToast = Toast.makeText(this, "Aктивити создано впервые", Toast.LENGTH_SHORT);
        }
        mToast.show();
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
        int id = view.getId();


        switch (id) {
            case R.id.checkBox:
                if (mCheckBox.isChecked()) {
                    mEditText.setVisibility(View.INVISIBLE);
                } else {
                    mEditText.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.button1:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.green));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.green_dark));
                }
                mToolbarColor = getResources().getColor(R.color.green);
                mStatusBar = getResources().getColor(R.color.green_dark);
                break;

            case R.id.button2:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.blue_dark));
                }
                mToolbarColor = getResources().getColor(R.color.blue);
                mStatusBar = getResources().getColor(R.color.blue_dark);
                break;

            case R.id.button3:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.red));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.red_dark));
                }
                mToolbarColor = getResources().getColor(R.color.red);
                mStatusBar = getResources().getColor(R.color.red_dark);
                break;

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            mToast = Toast.makeText(this, "Здесь будет меню", Toast.LENGTH_SHORT);
            mToast.show();
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Lg.e(TAG, "onSaveInstanceState()");
        outState.putBoolean(VISIBLE_KEY, mEditText.getVisibility() == View.VISIBLE);
        outState.putInt(COUNT_KEY, mCount);
        outState.putInt(TOOLBAR_COLOR, mToolbarColor);
        outState.putInt(STATUS_BAR_COLOR, mStatusBar);
        Lg.e(TAG, String.valueOf(mEditText.getVisibility() == View.VISIBLE));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Lg.e(TAG, "onRestoreInstanceState()");
        Lg.e(TAG, String.valueOf(savedInstanceState.getBoolean(VISIBLE_KEY)));
        int visibleState = savedInstanceState.getBoolean(VISIBLE_KEY) ? View.VISIBLE : View.INVISIBLE;

        int colorToolbar = savedInstanceState.getInt(TOOLBAR_COLOR);
        int colorStatusBar = savedInstanceState.getInt(STATUS_BAR_COLOR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(colorStatusBar);
        }
        mToolbar.setBackgroundColor(colorToolbar);
        mEditText.setVisibility(visibleState);
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



}
