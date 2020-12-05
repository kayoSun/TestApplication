package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.fragments.BaseFragment;
import com.example.myapplication.fragments.FragmentA;
import com.example.myapplication.fragments.FragmentB;
import com.example.myapplication.rxbus.RxBus;
import com.example.myapplication.rxbus.RxCallback;
import com.example.myapplication.rxbus.RxEvent;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements RxCallback {
    public String TAG = this.getClass().getSimpleName();

    TabLayout v_tablayout;
    List<BaseFragment> fragments;
    BaseFragment currentFragment;
    TextView v_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.v_hello).setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this,ScrollTestActivity.class);
            startActivity(intent);
        });
        v_tablayout=findViewById(R.id.v_tablayout);
        findViewById(R.id.v_send).setOnClickListener(v -> {
            RxBus.link().send(new RxEvent("",System.currentTimeMillis()));
        });
        RxBus.link().register(this,this);
        initTab();
//        initPage();
    }

    private void initTab() {
        v_tablayout.addTab( v_tablayout.newTab().setText("FragmentA"));
        v_tablayout.addTab( v_tablayout.newTab().setText("FragmentB"));
        v_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                changePage(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void initPage(){
        fragments = new ArrayList<>();
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (BaseFragment fragment : fragments) {
            fragmentTransaction.add(R.id.v_container,fragment);
            fragmentTransaction.hide(fragment);
        }
        currentFragment = fragments.get(0);
        fragmentTransaction.show(currentFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
    private void changePage(int index){
        if (fragments == null ||fragments.isEmpty()) {
            return;
        }
        BaseFragment baseFragment = fragments.get(index);
        if (currentFragment==baseFragment){
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(currentFragment);
        fragmentTransaction.show(baseFragment);
        currentFragment=baseFragment;
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxBus.link().unregister(this);
    }

    @Override
    public void onRxCall(RxEvent event) {
        Log.i(TAG, "onRxCall: "+event.value);

    }
}