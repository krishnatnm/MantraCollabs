package com.mantra.chatatmantra.view.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mantra.chatatmantra.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by rajat on 28/05/16.
 */
@EActivity(R.layout.tab_layout)
public class TabActivity extends AppCompatActivity {

    @ViewById(R.id.tabs)
    TabLayout tabs;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    @AfterViews
    public void init(){
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);


    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ContactFragment_(), "Appointment");
        adapter.addFrag(new ChatListFragment_(), "Chats");
        adapter.addFrag(new AppointmentFragment_(), "Contacts");
        viewPager.setAdapter(adapter);
    }



}
