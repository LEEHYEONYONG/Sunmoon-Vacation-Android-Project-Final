package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    public static String email;//MainActivity에서 받아온 이메일(아이디)
    ViewPager pager;
    TabLayout tab;
    PageAdapter ad;
    //ArrayList<Fragment> array;
    ArrayList<Fragment> array = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        getSupportActionBar().setTitle("주문");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tab=findViewById(R.id.tab);
        pager=findViewById(R.id.pager);


        tab.addTab(tab.newTab().setText("주문"));
        tab.getTabAt(0).setIcon(R.drawable.ic_baseline_local_grocery_store_24);
        tab.addTab(tab.newTab().setText("주문내역"));
        tab.getTabAt(1).setIcon(R.drawable.ic_baseline_featured_play_list_24);
        tab.addTab(tab.newTab().setText("정보"));
        tab.getTabAt(2).setIcon(R.drawable.ic_baseline_info_24);

        //array=new ArrayList<>();
        array.add(new OrderFragment());
        array.add(new ListFragment());
        array.add(new InformationFragment());

        ad = new PageAdapter(getSupportFragmentManager()/*,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT*/);
        pager.setAdapter(ad);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    class PageAdapter extends FragmentStatePagerAdapter{
        public PageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public PageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }




        @NonNull
        @Override
        public Fragment getItem(int position) {
            return array.get(position);
        }

        @Override
        public int getCount() {
            return array.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}