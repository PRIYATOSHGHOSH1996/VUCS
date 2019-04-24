package com.vucs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vucs.dao.ImageGalleryDAO;
import com.vucs.db.AppDatabase;
import com.vucs.model.ImageGalleryModel;

import java.util.List;

public class ImageGalleryViewModel extends AndroidViewModel {

    private ImageGalleryDAO imageGalleryDAO;

    public ImageGalleryViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        imageGalleryDAO = db.imageGalleryDAO();
    }


    public List<ImageGalleryModel> getAllImages() {
        return imageGalleryDAO.getAllImages();
    }

    ;


    public List<ImageGalleryModel> getAllImagesByFolder(String folderName) {
        return imageGalleryDAO.getAllImagesByFolder(folderName);
    }


    public List<String> getAllFolders() {
        return imageGalleryDAO.getAllFolders();
    }

}
