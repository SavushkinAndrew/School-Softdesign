package com.softdesign.school.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.softdesign.school.R;
import com.softdesign.school.data.storage.preferences.SaveUsers;
import com.softdesign.school.ui.activitys.MainActivity;
import com.softdesign.school.utils.ConstantManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    String state_view = "wait";
    @Bind({R.id.et_phone_wrapper, R.id.et_vk_wrapper, R.id.et_git_wrapper, R.id.et_email_wrapper, R.id.et_about_wrapper})
    List<TextInputLayout> inputLayoutList;
    @Bind({R.id.et_phone_value, R.id.et_vk_value, R.id.et_git_value, R.id.et_email_value, R.id.et_about_value})
    List<EditText> editTextList;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((MainActivity) getActivity()).selectedItem(R.id.drawer_profile, getResources().getString(R.string.drawer_profile));

        ButterKnife.bind(this, view);

        ((MainActivity)getActivity()).collapseAppBar(false);

        loadFields();

        ButterKnife.apply(editTextList, STARTVIEW);
        ButterKnife.apply(editTextList, INVISIBLE);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.main_fab);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
        params.setAnchorId(R.id.app_bar_layout);
        params.anchorGravity = Gravity.BOTTOM | Gravity.RIGHT;
        floatingActionButton.setLayoutParams(params);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(ConstantManager.WORK_STATE, state_view)) {
                    ButterKnife.apply(editTextList, INVISIBLE);
                    ButterKnife.apply(inputLayoutList, INVISIBLE);
                    floatingActionButton.setImageResource(R.drawable.ic_mode_edit_24dp);
                    state_view = "wait";
                } else {
                    if (Objects.equals(ConstantManager.WAIT_STATE, state_view)) {
                        ButterKnife.apply(editTextList, VISIBLE);
                        ButterKnife.apply(inputLayoutList, VISIBLE);
                        floatingActionButton.setImageResource(R.drawable.ic_done_24dp);
                        state_view = "work";
                    }
                }
            }
        });
        return view;
    }


    static ButterKnife.Action<View> INVISIBLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setEnabled(false);
            view.setFocusable(false);
            view.setFocusableInTouchMode(false);
            view.isClickable();
        }
    };

    static ButterKnife.Action<View> VISIBLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setEnabled(true);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        }
    };

    static ButterKnife.Action<View> STARTVIEW = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setFocusable(false);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        SaveFields();
    }

    @Override
    public void onStop() {
        super.onStop();

    }


    /**
     * Load fields from shared preferences
     */
    private void loadFields() {

        SaveUsers saveUsers = new SaveUsers();
        List<String> mFieldsValues = new ArrayList<>();
        mFieldsValues = saveUsers.loadFields();

        for (int i = 0; i < mFieldsValues.size(); i++) {
            editTextList.get(i).setText(mFieldsValues.get(i));
        }
    }

    /**
     * Save fields in shared preferences
     */
    private void SaveFields(){
        List<String> mUserValues = new ArrayList<>();

        for (int i = 0; i < editTextList.size(); i++) {
            mUserValues.add(editTextList.get(i).getText().toString());
        }

        SaveUsers saveUsers = new SaveUsers();
        saveUsers.saveUserFields(mUserValues);
    }

}

