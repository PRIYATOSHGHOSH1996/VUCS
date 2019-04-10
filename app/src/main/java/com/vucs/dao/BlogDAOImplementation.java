package com.vucs.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.vucs.db.DatabaseHandler;
import com.vucs.model.BlogModel;

import java.util.ArrayList;
import java.util.List;

public class BlogDAOImplementation extends DatabaseHandler implements BlogDAO {

    public BlogDAOImplementation(Context context) {
        super(context);
    }

    @Override
    public void addBlog(BlogModel blogModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, blogModel.getBlogId());
        values.put(KEY_TITLE, blogModel.getBlogTitle());
        values.put(KEY_BY, blogModel.getBlogBy());
        values.put(KEY_DATE, blogModel.getDate());
        values.put(KEY_CONTENT, blogModel.getContent());
        values.put(KEY_URL, blogModel.getBlogImageURL());

        // Inserting Row
        if (db.insert(DT_BLOG, null, values) == -1) {
            Log.e("addBlog", "Replacing Blog module");
            db.updateWithOnConflict(DT_BLOG,values,null,null,SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close(); // Closing database connection

    }

    @Override
    public void addBlogs(List<BlogModel> blogModels) {


    }

    @Override
    public List<BlogModel> getAllBlog() {
        List<BlogModel> blogModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(DT_BLOG,
                    new String[] {KEY_ID,KEY_TITLE,KEY_BY, KEY_DATE, KEY_CONTENT,KEY_URL},
                    null,
                    null,
                    null, null, null, null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                try{
                    do {
                        BlogModel blogModel = new BlogModel(cursor.getInt(0),cursor.getString(1),
                                cursor.getString(2),cursor.getLong(3), cursor.getString(4),cursor.getString(5));
                        blogModels.add(blogModel);
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
        return blogModels;
    }
}