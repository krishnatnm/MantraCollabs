package com.mantra.chatatmantra.view.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mantra.chatatmantra.R;
import com.mantra.chatatmantra.view.adapters.ChatsAdapter;

import org.androidannotations.annotations.EFragment;

/**
 * Created by rajat on 28/05/16.
 */
@EFragment(R.layout.chat_list_fragment_layout)
public class ChatListFragment extends Fragment {

    View rootView;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chat_list_fragment_layout, container, false);
        initView();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChatsAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.clfl_rv);
    }

}