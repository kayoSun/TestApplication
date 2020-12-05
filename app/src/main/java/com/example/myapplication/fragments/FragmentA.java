package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

/**
 * Sunlley
 * 2020/11/27
 * -----------------
 */
public class FragmentA extends BaseFragment{
    RecyclerView v_recyclerview;

    @Override
    protected int layout() {
        return R.layout.fragment_test;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v_recyclerview = view.findViewById(R.id.v_recyclerview);
        v_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        v_recyclerview.setAdapter(new FragmentAAdapter());
    }

    class FragmentAAdapter extends RecyclerView.Adapter<FragmentAHolder>{

        @NonNull
        @Override
        public FragmentAHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FragmentAHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentAHolder holder, int position) {
            holder.bind(position+"");
        }


        @Override
        public int getItemCount() {
            return 50;
        }
    }

    class FragmentAHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public FragmentAHolder(@NonNull View itemView) {
            super(itemView);
            textView= (TextView) itemView;
        }
        public void bind(String text){
            textView.setText("FragmentAHolder:"+text);
        }
    }

}
