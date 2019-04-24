package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.BlogDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.BlogModel;

import java.util.List;

public class BlogViewModel extends AndroidViewModel {

    private BlogDAO blogDAO;

    public BlogViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        blogDAO = db.blogDAO();
    }

    public List<BlogModel> getAllBlog() {
        return blogDAO.getAllBlog();
    }
}

