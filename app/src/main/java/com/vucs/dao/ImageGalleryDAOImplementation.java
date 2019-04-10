package com.vucs.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vucs.db.DatabaseHandler;
import com.vucs.model.EventModel;
import com.vucs.model.ImageGalleryModel;

import java.util.ArrayList;
import java.util.List;

public class ImageGalleryDAOImplementation extends DatabaseHandler implements ImageGalleryDAO {

    public ImageGalleryDAOImplementation(Context context) {
        super(context);
    }


    @Override
    public List<ImageGalleryModel> getAllImages() {
        List<ImageGalleryModel> imageGalleryModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(DT_IMAGE_GALLERY,
                    new String[] {KEY_ID,KEY_FOLDER_NAME,KEY_URL},
                    null,
                    null,
                    null, null, null, null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                try{
                    do {
                        ImageGalleryModel imageGalleryModel = new ImageGalleryModel(cursor.getInt(0),cursor.getString(1),
                                cursor.getString(2));
                        imageGalleryModels.add(imageGalleryModel);
                    }while (cursor.moveToNext());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            try {
                if(cursor != null)
                    cursor.close();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageGalleryModels;
    }

    @Override
    public List<ImageGalleryModel> getAllImagesByFolder(String folderName) {
        List<ImageGalleryModel> imageGalleryModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(DT_IMAGE_GALLERY,
                    new String[] {KEY_ID,KEY_FOLDER_NAME,KEY_URL},
                    KEY_FOLDER_NAME,
                    new String[]{String.valueOf(folderName)},
                    null, null, null, null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                try{
                    do {
                        ImageGalleryModel imageGalleryModel = new ImageGalleryModel(cursor.getInt(0),cursor.getString(1),
                                cursor.getString(2));
                        imageGalleryModels.add(imageGalleryModel);
                    }while (cursor.moveToNext());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            try {
                if(cursor != null)
                    cursor.close();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageGalleryModels;

    }

    @Override
    public List<String> getAllFolders() {
        List<String> folderNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(DT_IMAGE_GALLERY,
                    new String[] {KEY_FOLDER_NAME},
                    null,
                   null,
                    KEY_FOLDER_NAME, null, null, null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                try{
                    do {
                        folderNames.add(cursor.getString(0));
                    }while (cursor.moveToNext());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            try {
                if(cursor != null)
                    cursor.close();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return folderNames;

    }

    @Override
    public void insertImage(ImageGalleryModel imageGalleryModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, imageGalleryModel.getImageId());
        values.put(KEY_FOLDER_NAME, imageGalleryModel.getFolderName());
        values.put(KEY_URL, imageGalleryModel.getImageURL());


        // Inserting Row
        if (db.insert(DT_IMAGE_GALLERY, null, values) == -1) {
            Log.e("add image", "Replacing image gallery module");
            db.updateWithOnConflict(DT_IMAGE_GALLERY,values,null,null,SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close(); // Closing database connection

    }
}