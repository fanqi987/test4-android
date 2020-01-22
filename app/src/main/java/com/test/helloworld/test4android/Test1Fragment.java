package com.test.helloworld.test4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.helloworld.test4android.adapter.RecyclerViewAdapter;
import com.test.helloworld.test4android.bean.BeanFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Test1Fragment extends Fragment {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BeanFactory factory = BeanFactory.getInstance();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(factory.getBeans(), view.getContext());
        recyclerView.setAdapter(adapter);
    }
}
