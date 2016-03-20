package com.softdesign.school.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.softdesign.school.R;
import com.softdesign.school.data.storage.preferences.SaveUsers;
import com.softdesign.school.ui.activitys.MainActivity;
import com.softdesign.school.utils.ArrayHelper;
import com.softdesign.school.utils.ConstantManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFRAGMENT extends Fragment {

    String state_view = "wait";
    @Bind({R.id.et_phone_wrapper, R.id.et_mail_wrapper, R.id.et_vk_wrapper, R.id.et_about_user_wrapper})
    List<TextInputLayout> inputLayoutList;
    @Bind({R.id.et_phone_value, R.id.et_mail_value, R.id.et_vk_value, R.id.et_about_user_value})
    List<EditText> editTextList;
    @Bind({R.id.txt_phone_label, R.id.txt_mail_label, R.id.txt_vk_label, R.id.txt_about_user_label})
    List<TextView> textLabelViewList;
    @Bind({R.id.txt_phone_value, R.id.txt_mail_value, R.id.txt_vk_value, R.id.txt_about_user_value})
    List<TextView> textValueViewList;
    List<TextView> createdTextViewsLabel;
    List<TextView> createdTextViewsValue;
    List<EditText> createdEditViewFields = new ArrayList<>();
    @Bind({R.id.icon_hangout, R.id.ic_mail_right, R.id.icon_vk_visibility})
    List<ImageView> imageViewList;
    @Bind(R.id.icon_github_right)
    ImageView ic_github_right;
    @Bind(R.id.linear_repository_field_wrapper)
    LinearLayout linearRepository;
    @Bind(R.id.et_create_field)
    EditText etCreateRepoField;

    @Bind(R.id.linear_txt_github_wrapper)
    LinearLayout linearTextViewWrapper;

    @Bind(R.id.relative_wrapper_icon_right)
    RelativeLayout relativeIconRightWrapper;

    @Bind(R.id.txt_github_label)
    TextView txtGitHubLabel;
    @Bind(R.id.txt_github_value)
    TextView txtGitHubValue;

    List<String> listValueRepository = new ArrayList<>();

    ImageView icon_right;

    LinearLayout.LayoutParams editTextParams;
    ArrayHelper arrayHelper;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        createdTextViewsValue = new ArrayList<>();
        createdTextViewsLabel = new ArrayList<>();

        ((MainActivity) getActivity()).selectedItem(R.id.drawer_profile, getResources().getString(R.string.drawer_profile));
        ((MainActivity) getActivity()).collapseAppBar(false);

        ButterKnife.bind(this, view);

        arrayHelper = new ArrayHelper(getActivity());
        if (listValueRepository.size() == 0) {
            this.listValueRepository = arrayHelper.getArray(ConstantManager.REPOSITORY_FIELDS_VALUES);
        }

        createRepoTextView(listValueRepository);

        loadFields();

        ButterKnife.apply(editTextList, STARTVIEW);
        ButterKnife.apply(editTextList, INVISIBLE);
        etCreateRepoField.setVisibility(View.GONE);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.main_fab);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
        params.setAnchorId(R.id.app_bar_layout);
        params.anchorGravity = Gravity.BOTTOM | Gravity.RIGHT;
        floatingActionButton.setLayoutParams(params);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listValueRepository = arrayHelper.getArray(ConstantManager.REPOSITORY_FIELDS_VALUES);

                if (Objects.equals(ConstantManager.WORK_STATE, state_view)) {

                    ButterKnife.apply(editTextList, INVISIBLE);
                    ButterKnife.apply(inputLayoutList, INVISIBLE);
                    ButterKnife.apply(textValueViewList, VISIBLE);
                    ButterKnife.apply(textLabelViewList, VISIBLE);
                    ButterKnife.apply(imageViewList, VISIBLE);
                    for (int i = 0; i < createdEditViewFields.size(); i++) {
                        createdEditViewFields.get(i).setVisibility(View.GONE);
                    }
                    etCreateRepoField.setVisibility(View.GONE);



                    floatingActionButton.setImageResource(R.drawable.ic_mode_edit_24dp);
                    ic_github_right.setImageResource(R.drawable.ic_visibility_24dp);
                    createdTextViewsValue.clear();
                    createdTextViewsLabel.clear();
                    createRepoTextView(listValueRepository);

                    state_view = "wait";
                    passInfoField(state_view);
                } else {
                    if (Objects.equals(ConstantManager.WAIT_STATE, state_view)) {
                        ButterKnife.apply(editTextList, VISIBLE);
                        ButterKnife.apply(inputLayoutList, VISIBLE);
                        ButterKnife.apply(textValueViewList, INVISIBLE);
                        ButterKnife.apply(textLabelViewList, INVISIBLE);
                        ButterKnife.apply(imageViewList, INVISIBLE);
                        for (int i = 0; i < listValueRepository.size(); i++) {
                            createdTextViewsLabel.get(i).setVisibility(View.GONE);
                            createdTextViewsValue.get(i).setVisibility(View.GONE);
                        }

                        txtGitHubValue.setVisibility(View.GONE);
                        txtGitHubLabel.setVisibility(View.GONE);
                        etCreateRepoField.setVisibility(View.VISIBLE);
                        /*linearRepository.setVisibility(View.VISIBLE);*/


                        Log.e("size", listValueRepository.size() + " ");
                        repositoryFieldCreator(listValueRepository);

                        editTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        editTextParams.rightMargin = relativeIconRightWrapper.getWidth();

                        etCreateRepoField.setLayoutParams(editTextParams);

                        floatingActionButton.setImageResource(R.drawable.ic_done_24dp);
                        ic_github_right.setImageResource(R.drawable.ic_add_circle_24dp);
                        ic_github_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (!Objects.equals(etCreateRepoField.getText().toString(), "")) {

                                    Log.e("create new","FRAME");

                                    linearRepository.addView(newFrame(), 1);

                                } else {
                                    Snackbar.make(getView(), "Напишите название репозитория", Snackbar.LENGTH_SHORT).show();
                                }
                                arrayHelper.saveArray(ConstantManager.REPOSITORY_FIELDS_VALUES, listValueRepository);
                                listValueRepository = arrayHelper.getArray(ConstantManager.REPOSITORY_FIELDS_VALUES);
                            }
                        });

                        state_view = "work";
                        passInfoField(state_view);


                    }
                }
            }
        });
        return view;
    }

    private FrameLayout newFrame() {
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(frameParams);

        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setLayoutParams(relativeIconRightWrapper.getLayoutParams());

        //relativeLayout.addView(newImageDelete(frameLayout));

        frameLayout.addView(newRepoEditText());
        frameLayout.addView(relativeLayout);

        return frameLayout;
    }


    static ButterKnife.Action<View> INVISIBLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(View.GONE);
            view.setEnabled(false);
            view.setFocusable(false);
            view.setFocusableInTouchMode(false);
            view.isClickable();
        }
    };

    static ButterKnife.Action<View> VISIBLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(View.VISIBLE);
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


    /**
     * Load fields from shared preferences
     */
    private void loadFields() {

        SaveUsers saveUsers = new SaveUsers();
        List<String> mFieldsValues = new ArrayList<>();
        mFieldsValues = saveUsers.loadFields();

        for (int i = 0; i < mFieldsValues.size(); i++) {
            textValueViewList.get(i).setText(mFieldsValues.get(i));
        }
    }

    /**
     * Save fields in shared preferences
     */
    private void saveFields() {
        List<String> mUserValues = new ArrayList<>();

        for (int i = 0; i < editTextList.size(); i++) {
            mUserValues.add(editTextList.get(i).getText().toString());
        }

        SaveUsers saveUsers = new SaveUsers();
        saveUsers.saveUserFields(mUserValues);
    }

    private void passInfoField(String state) {

        if (Objects.equals(state, "wait")) {
            for (int i = 0; i < textValueViewList.size(); i++) {
                textValueViewList.get(i).setText(editTextList.get(i).getText());
            }
        } else {
            for (int i = 0; i < editTextList.size(); i++) {
                editTextList.get(i).setText(textValueViewList.get(i).getText());
            }
        }

    }


    private void repositoryFieldCreator(List<String> size) {
        for (int i = 0; i < size.size(); i++) {

            Log.e("CREATE","CREATE");

            FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final FrameLayout frameLayout = new FrameLayout(getActivity());
            frameLayout.setLayoutParams(frameParams);

            RelativeLayout relativeLayout = new RelativeLayout(getActivity());
            relativeLayout.setLayoutParams(relativeIconRightWrapper.getLayoutParams());



            editTextParams = new LinearLayout.LayoutParams(etCreateRepoField.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            editTextParams.rightMargin = ic_github_right.getWidth();

            final EditText editText = new EditText(getActivity());
            editText.setHint(R.string.fragment_profile_create_git_label);
            editText.setHintTextColor(getResources().getColor(R.color.grey));
            editText.setTextSize(16);
            editText.setText(size.get(i));
            editText.setLayoutParams(editTextParams);

            createdEditViewFields.add(editText);

            frameLayout.addView(editText);
            frameLayout.addView(relativeLayout);

            linearRepository.addView(frameLayout);



        }
    }

    private ImageView newImageDelete(final FrameLayout frameLayout){
        Log.e("create","image");
        icon_right = new ImageView(getActivity());
        icon_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linearRepository.removeView(frameLayout);
            }
        });
        icon_right.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_24dp));

        icon_right.setLayoutParams(ic_github_right.getLayoutParams());

        return icon_right;
    }

    private EditText newRepoEditText() {

        editTextParams = new LinearLayout.LayoutParams(etCreateRepoField.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextParams.rightMargin = ic_github_right.getWidth();

        final EditText editText = new EditText(getActivity());
        editText.setHint(R.string.fragment_profile_create_git_label);
        editText.setHintTextColor(getResources().getColor(R.color.grey));
        editText.setTextSize(16);
        editText.setLayoutParams(editTextParams);

        editText.setText(etCreateRepoField.getText());

        listValueRepository.add(etCreateRepoField.getText().toString());

        Log.e("create View", listValueRepository.size() + " ");

        etCreateRepoField.setText("");

        createdEditViewFields.add(editText);

        return editText;
    }

    private void createRepoTextView(List<String> size) {

        if (size.size() == 0) {

            txtGitHubLabel.setVisibility(View.VISIBLE);
            txtGitHubValue.setVisibility(View.VISIBLE);

        } else {

            txtGitHubLabel.setVisibility(View.GONE);
            txtGitHubValue.setVisibility(View.GONE);

            for (int i = 0; i < size.size(); i++) {
                TextView textLabel = new TextView(getActivity());
                textLabel.setTextColor(getResources().getColor(R.color.grey));
                textLabel.setText("Репозиторий");

                TextView textValue = new TextView(getActivity());
                textValue.setTextColor(getResources().getColor(R.color.black));
                textValue.setText(size.get(i));

                linearTextViewWrapper.addView(textLabel);
                linearTextViewWrapper.addView(textValue);

                createdTextViewsLabel.add(textLabel);
                createdTextViewsValue.add(textValue);
            }

        }

    }


    @Override
    public void onPause() {
        super.onPause();
        saveFields();
        arrayHelper.saveArray(ConstantManager.REPOSITORY_FIELDS_VALUES, listValueRepository);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

}

