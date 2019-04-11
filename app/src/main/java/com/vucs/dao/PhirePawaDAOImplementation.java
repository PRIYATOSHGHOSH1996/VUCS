package com.vucs.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vucs.db.DatabaseHandler;
import com.vucs.model.NoticeModel;
import com.vucs.model.PhirePawaProfileModel;

import java.util.ArrayList;
import java.util.List;


public class PhirePawaDAOImplementation extends DatabaseHandler implements PhirePawaProfileDAO {

    public PhirePawaDAOImplementation(Context context) {
        super(context);
    }



    @Override
    public List<PhirePawaProfileModel> getAllUser() {
        List<PhirePawaProfileModel> phirePawaProfileModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(DT_PHIRE_PAWA,
                    new String[] {KEY_ID,KEY_NAME,KEY_BATCH,KEY_COMPANY,KEY_URL},
                    null,
                    null,
                    null, null, null, null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                try{
                    do {
                        PhirePawaProfileModel phirePawaProfileModel = new PhirePawaProfileModel(cursor.getInt(0),cursor.getString(1),
                                cursor.getInt(2),cursor.getString(3), cursor.getString(4));
                        phirePawaProfileModels.add(phirePawaProfileModel);
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
        return phirePawaProfileModels;
    }

    @Override
    public void insertUser(PhirePawaProfileModel phirePawaProfileModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, phirePawaProfileModel.getName());
        values.put(KEY_BATCH, phirePawaProfileModel.getBatch());
        values.put(KEY_COMPANY, phirePawaProfileModel.getCompany());
        values.put(KEY_URL, phirePawaProfileModel.getUserImageURL());

        // Inserting Row
        if (db.insert(DT_PHIRE_PAWA, null, values) == -1) {
            Log.e("addphire pawa", "Replacing phire pawa module");
            db.updateWithOnConflict(DT_PHIRE_PAWA,values,null,null,SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close(); // Closing database connection


    }
}