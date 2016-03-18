package com.softdesign.school.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softdesign.school.R;
import com.softdesign.school.data.storage.model.Contacts;
import com.softdesign.school.ui.activitys.MainActivity;
import com.softdesign.school.ui.adapters.ContactAdapter;

import java.util.ArrayList;
import java.util.List;


public class ContactsFragment extends Fragment {

    private RecyclerView mListContacts;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;

    List<Contacts> mUsers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts,container,false);

        ((MainActivity) getActivity()).selectedItem(R.id.drawer_contacts, getResources().getString(R.string.drawer_contacts));

        fab = (FloatingActionButton)getActivity().findViewById(R.id.main_fab);
        fab.setVisibility(View.GONE);



        mListContacts = (RecyclerView)view.findViewById(R.id.contacts_recycler_view);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mListContacts.setLayoutManager(mLayoutManager);
        getInfo();
        ContactAdapter contactAdapter = new ContactAdapter(mUsers);
        mListContacts.setAdapter(contactAdapter);

        ((MainActivity) getActivity()).collapseAppBar(true);
        return view;
    }

    private List<Contacts> getInfo() {

        mUsers = new ArrayList<>();

        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));
        mUsers.add(new Contacts("Andrew","Savushkin"));

        return mUsers;
    }

}
