package com.vucs.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vucs.R;
import com.vucs.adapters.RecyclerViewBlogAdapter;
import com.vucs.model.BlogModel;
import com.vucs.viewmodel.BlogViewModel;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BlogFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewBlogAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blog, container, false);
        initView();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewBlogAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        BlogViewModel blogViewModel = ViewModelProviders.of(this).get(BlogViewModel.class);
        blogViewModel.getAllBlog().observe(this, new Observer<List<BlogModel>>() {
            @Override
            public void onChanged(List<BlogModel> blogModels) {
                adapter.addBlog(blogModels);
            }
        });



    }


}
