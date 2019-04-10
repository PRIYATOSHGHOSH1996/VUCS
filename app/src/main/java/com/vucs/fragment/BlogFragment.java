package com.vucs.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vucs.R;
import com.vucs.adapters.RecyclerViewBlogAdapter;
import com.vucs.dao.BlogDAO;
import com.vucs.dao.BlogDAOImplementation;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BlogFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewBlogAdapter adapter;
    private BlogDAO blogDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blog, container, false);
        blogDAO = new BlogDAOImplementation(getContext());
        initView();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewBlogAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addBlog(blogDAO.getAllBlog());
        recyclerView.setAdapter(adapter);


    }


}
