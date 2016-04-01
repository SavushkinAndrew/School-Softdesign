package com.softdesign.school.ui.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.softdesign.school.R;
import com.softdesign.school.data.managers.events.EventBus;
import com.softdesign.school.data.managers.events.EventGitValue;
import com.softdesign.school.data.storage.preferences.SaveUsers;
import com.softdesign.school.ui.activitys.MainActivity;
import com.softdesign.school.ui.adapters.ProfileGitAdapter;
import com.softdesign.school.ui.view.ResizeAnimation;
import com.softdesign.school.utils.ArrayHelper;
import com.softdesign.school.utils.ConstantManager;
import com.softdesign.school.utils.SwipeDismissListViewTouchListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFRAGMENT extends Fragment {

    String state_view = "wait";
    @Bind({R.id.et_phone_wrapper, R.id.et_email_wrapper, R.id.et_vk_wrapper, R.id.et_bio_wrapper})
    List<TextInputLayout> inputLayoutList;
    @Bind({R.id.et_phone_value, R.id.et_email_value, R.id.et_vk_value, R.id.et_bio_value})
    List<EditText> editTextList;
    @Bind({R.id.txt_phone_label, R.id.txt_email_label, R.id.txt_vk_label, R.id.txt_bio_label})
    List<TextView> textLabelViewList;
    @Bind({R.id.txt_phone_value, R.id.txt_email_value, R.id.txt_vk_value, R.id.txt_bio_value})
    List<TextView> textValueViewList;
    @Bind({R.id.icon_hangout, R.id.ic_mail_right, R.id.icon_vk_visibility})
    List<ImageView> imageViewList;
    @Bind(R.id.icon_github_right)
    ImageView icGithubRight;
    @Bind(R.id.et_create_field)
    EditText etCreateRepoField;
    @Bind(R.id.git_list)
    ListView repoList;
    @Bind(R.id.empty_list_view)
    TextView emptyText;
    @Bind(R.id.empty_list_null_view)
    TextView emptyTextNull;
    @Bind(R.id.scroll)
    NestedScrollView nestedScrollView;

    List<String> listValueRepository = new ArrayList<>();

    ArrayHelper arrayHelper;

    ProfileGitAdapter gitAdapter;

    @Bind(R.id.main_info_container)
    LinearLayout mSecondBar;

    FloatingActionButton floatingActionButton;

    EventBus eventBus = EventBus.getInstance();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        arrayHelper = new ArrayHelper(getActivity());
        if (listValueRepository.size() == 0) {
            this.listValueRepository = arrayHelper.getArray(ConstantManager.REPOSITORY_FIELDS_VALUES);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((MainActivity) getActivity()).selectedItem(R.id.drawer_profile, getResources().getString(R.string.drawer_profile));
        ((MainActivity) getActivity()).collapseAppBar(false);

        ButterKnife.bind(this, view);

        loadFields();

        ButterKnife.apply(editTextList, GONE);
        etCreateRepoField.setVisibility(View.GONE);
        icGithubRight.setVisibility(View.GONE);

        repoList.setAdapter(getGitAdapter());

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linebetweenlayout);

                DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
                int px = Math.round(100 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

                int diff = (view.getBottom() - (v.getHeight() + v.getScrollY()));

                if (diff < 0) {
                    ResizeAnimation resizeAnimation = new ResizeAnimation(mSecondBar, linearLayout.getHeight() + 20);
                    resizeAnimation.setDuration(65);
                    mSecondBar.startAnimation(resizeAnimation);
                } else {
                    ResizeAnimation resizeAnimation = new ResizeAnimation(mSecondBar, px);
                    resizeAnimation.setDuration(65);
                    mSecondBar.startAnimation(resizeAnimation);
                }
            }
        });

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        repoList,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    listValueRepository.remove(gitAdapter.getItem(position));
                                }
                                gitAdapter.notifyDataSetChanged();

                                eventBus.post(new EventGitValue(listValueRepository));
                            }
                        });
        repoList.setOnTouchListener(touchListener);
        repoList.setOnScrollListener(touchListener.makeScrollListener());

        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.main_fab);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
        params.setAnchorId(R.id.app_bar_layout);
        params.anchorGravity = Gravity.BOTTOM | Gravity.RIGHT;
        floatingActionButton.setLayoutParams(params);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeState();

                if (Objects.equals(ConstantManager.WAIT_STATE, state_view)) {

                    listValueRepository = arrayHelper.getArray(ConstantManager.REPOSITORY_FIELDS_VALUES);

                    loadFields();

                    repoList.setAdapter(getGitAdapter());

                    ButterKnife.apply(editTextList, GONE);
                    ButterKnife.apply(inputLayoutList, GONE);
                    ButterKnife.apply(textValueViewList, EDITOR);
                    ButterKnife.apply(textLabelViewList, EDITOR);
                    ButterKnife.apply(imageViewList, EDITOR);

                    etCreateRepoField.setVisibility(View.GONE);
                    icGithubRight.setVisibility(View.GONE);

                    floatingActionButton.setImageResource(R.drawable.ic_mode_edit_24dp);
                    icGithubRight.setImageResource(R.drawable.ic_visibility_24dp);

                } else {
                    if (Objects.equals(ConstantManager.EDIT_STATE, state_view)) {

                        icGithubRight.setVisibility(View.VISIBLE);
                        etCreateRepoField.setVisibility(View.VISIBLE);

                        repoList.setAdapter(getGitAdapter());

                        ButterKnife.apply(editTextList, EDITOR);
                        ButterKnife.apply(inputLayoutList, EDITOR);
                        ButterKnife.apply(textValueViewList, GONE);
                        ButterKnife.apply(textLabelViewList, GONE);
                        ButterKnife.apply(imageViewList, GONE);

                        floatingActionButton.setImageResource(R.drawable.ic_done_24dp);
                        icGithubRight.setImageResource(R.drawable.ic_add_circle_24dp);

                        icGithubRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (listValueRepository.size() == 3) {
                                    Toast.makeText(getActivity(), "Максимальное количество репозиториев 3", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!Objects.equals(etCreateRepoField.getText().toString(), "")) {

                                        listValueRepository.add(etCreateRepoField.getText().toString());

                                        etCreateRepoField.setText("");

                                        eventBus.post(new EventGitValue(listValueRepository));

                                    } else {
                                        Snackbar.make(getView(), "Напишите название репозитория", Snackbar.LENGTH_SHORT).show();
                                    }
                                    arrayHelper.saveArray(ConstantManager.REPOSITORY_FIELDS_VALUES, listValueRepository);
                                    listValueRepository = arrayHelper.getArray(ConstantManager.REPOSITORY_FIELDS_VALUES);
                                }
                            }
                        });

                        saveFields();
                    }
                }
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    private void changeState() {

        if (state_view.equals("wait")) {
            state_view = "edit";
            passInfoField();
        } else {
            if (state_view.equals("edit")) {
                state_view = "wait";
                saveFields();
            }
        }
        loadFields();
    }

    static ButterKnife.Action<View> GONE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(View.GONE);
        }
    };

    static ButterKnife.Action<View> EDITOR = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(View.VISIBLE);
        }
    };


    /**
     * Load fields from shared preferences
     */
    private void loadFields() {

        SaveUsers saveUsers = new SaveUsers();
        List<String> mFieldsValues = saveUsers.loadFields();

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

    private void passInfoField() {
        for (int i = 0; i < editTextList.size(); i++) {
            editTextList.get(i).setText(textValueViewList.get(i).getText());
        }
    }

    private ProfileGitAdapter getGitAdapter() {

        DisplayMetrics displayMetrics;
        int px = 0;
        ViewGroup.LayoutParams gitListParams;
        int height;

        displayMetrics = getContext().getResources().getDisplayMetrics();

        if (state_view.equals(ConstantManager.WAIT_STATE)) {
            height = 77;
            px = Math.round(height * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            gitAdapter = new ProfileGitAdapter(listValueRepository, (MainActivity) getActivity(), R.layout.item_git_mode_wait);
            emptyText.setText(R.string.fragment_profile_git_label);
            emptyTextNull.setVisibility(View.GONE);
            repoList.setEmptyView(emptyText);
        } else {
            if (state_view.equals(ConstantManager.EDIT_STATE)) {
                height = 47;
                px = Math.round(height * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                gitAdapter = new ProfileGitAdapter(listValueRepository, (MainActivity) getActivity(), R.layout.item_git_mode_edit);
                emptyText.setVisibility(View.GONE);
                repoList.setEmptyView(emptyTextNull);
            }
        }

        gitListParams = repoList.getLayoutParams();
        gitListParams.height = listValueRepository.size() * px;
        repoList.setLayoutParams(gitListParams);

        return gitAdapter;
    }


    @Subscribe
    public void refreshGitValue(EventGitValue eventGitValue) {
        arrayHelper.saveArray(ConstantManager.REPOSITORY_FIELDS_VALUES, listValueRepository);
        listValueRepository = arrayHelper.getArray(ConstantManager.REPOSITORY_FIELDS_VALUES);
        repoList.setAdapter(getGitAdapter());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eventBus.unregister(this);
    }
}

