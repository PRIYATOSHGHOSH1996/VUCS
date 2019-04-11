package com.vucs.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vucs.db.DatabaseHandler;
import com.vucs.model.BlogModel;
import com.vucs.model.NoticeModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;


public class NoticeDAOImplementation extends DatabaseHandler implements NoticeDAO {

    public NoticeDAOImplementation(Context context) {
        super(context);
    }



    @Override
    public List<NoticeModel> getAllNotice() {
        List<NoticeModel> noticeModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(DT_NOTICE,
                    new String[] {KEY_ID,KEY_TITLE,KEY_DATE,KEY_BY,KEY_URL},
                    null,
                    null,
                    null, null, null, null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                try{
                    do {
                        NoticeModel noticeModel = new NoticeModel(cursor.getInt(0),cursor.getString(1),
                                cursor.getLong(2),cursor.getString(3), cursor.getString(4));
                        noticeModels.add(noticeModel);
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
        return noticeModels;
    }

    @Override
    public void insertNotice(NoticeModel noticeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, noticeModel.getNoticeTitle());
        values.put(KEY_BY, noticeModel.getNoticeBy());
        values.put(KEY_DATE, noticeModel.getDate());
        values.put(KEY_URL, noticeModel.getDownloadURL());

        // Inserting Row
        if (db.insert(DT_NOTICE, null, values) == -1) {
            Log.e("addnotice", "Replacing notice module");
            db.updateWithOnConflict(DT_NOTICE,values,null,null,SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close(); // Closing database connection

    }
}