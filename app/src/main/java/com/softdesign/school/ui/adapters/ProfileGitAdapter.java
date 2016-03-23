package com.softdesign.school.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softdesign.school.R;
import com.softdesign.school.ui.activitys.MainActivity;

import java.util.List;

import butterknife.Bind;

public class ProfileGitAdapter extends BaseAdapter {

    MainActivity mContext;
    List<String> mGitValues;
    LayoutInflater mInflater;
    int layoutResource;

    public ProfileGitAdapter(List<String> mGitValues, MainActivity mContext, int layoutResource) {
        this.mGitValues = mGitValues;
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutResource = layoutResource;
    }

    @Override
    public int getCount() {
        return mGitValues.size();
    }

    @Override
    public Object getItem(int position) {
        return mGitValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView=mInflater.inflate(layoutResource, parent, false);
        }

        TextView fullName = (TextView) itemView.findViewById(R.id.txt_git_value);
        EditText editText = (EditText) itemView.findViewById(R.id.et_git_value);
        if (fullName != null){
            fullName.setText(mGitValues.get(position));
        }
        if (editText != null){
            editText.setText(mGitValues.get(position));
        }

        ImageView iconDeleteItem = (ImageView) itemView.findViewById(R.id.ic_delete_item_list);

        return itemView;
    }



}
