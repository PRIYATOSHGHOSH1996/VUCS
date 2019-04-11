package com.vucs.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vucs.db.DatabaseHandler;
import com.vucs.model.BlogModel;
import com.vucs.model.EventModel;

import java.util.ArrayList;
import java.util.List;

public class EventDAOImplementation extends DatabaseHandler implements EventDAO {

    public EventDAOImplementation(Context context) {
        super(context);
    }

    @Override
    public void insertEvent(EventModel eventModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, eventModel.getEventTitle());
        values.put(KEY_DESCRIPTION, eventModel.getEventDescription());
        values.put(KEY_DATE, eventModel.getDate());


        // Inserting Row
        if (db.insert(DT_EVENT, null, values) == -1) {
            Log.e("insertBlog", "Replacing Event module");
            db.updateWithOnConflict(DT_EVENT,values,null,null,SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close(); // Closing database connection

    }

    @Override
    public void addEvents(List<EventModel> eventModels) {

    }

    @Override
    public List<EventModel> getAllEvent() {
        List<EventModel> eventModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(DT_EVENT,
                    new String[] {KEY_ID,KEY_TITLE,KEY_DESCRIPTION, KEY_DATE},
                    null,
                    null,
                    null, null, null, null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                try{
                    do {
                        EventModel eventModel = new EventModel(cursor.getInt(0),cursor.getString(1),
                                cursor.getString(2),cursor.getLong(3));
                        eventModels.add(eventModel);
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
        return eventModels;
    }
}