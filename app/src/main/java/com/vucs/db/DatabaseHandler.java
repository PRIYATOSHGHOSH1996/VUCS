package com.vucs.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.vucs.App;
import com.vucs.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Aditya on 05-June-18.
 * Is_synced for dt_saved_responses
    Completed  ->  0
    Incomplete ->  2
    Synced     ->  1
 *Is_completed for dt_responses
    Completed  ->  0
    Incomplete ->  2
    Trash      -> -1
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = App.getContext().getString(R.string.database_name);
    public static final String APP_FOLDER = "TRACKBEE SURVEY";
    // Table names
    public static final String DT_BLOG = "dt_blog";
    public static final String DT_EVENT = "dt_event";
    public static final String DT_IMAGE_GALLERY = "dt_image_gallery";
    public static final String DT_NOTICE = "dt_notice";
    public static final String DT_PHIRE_PAWA = "dt_phire_pawa";

    // Table Column names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_BY = "created_by";
    public static final String KEY_DATE = "date";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_URL = "url";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_FOLDER_NAME = "folder_name";
    public static final String KEY_NAME = "name";
    public static final String KEY_BATCH = "batch";
    public static final String KEY_COMPANY = "company";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
       createBlogTable(db);
       createEventTable(db);
       createImageGalleryTable(db);
       createNoticeTable(db);
       createPhirePawaProfileTable(db);
    }

    private void createBlogTable(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + DT_BLOG + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_BY + " TEXT,"
                + KEY_DATE + " LONG,"
                + KEY_CONTENT + " TEXT,"
                + KEY_URL + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }


    private void createEventTable(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + DT_EVENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT ,"
                + KEY_DATE + " LONG "
                + ")";
        db.execSQL(CREATE_TABLE);
    }


    private void createImageGalleryTable(SQLiteDatabase db){

        String  CREATE_TABLE = "CREATE TABLE " + DT_IMAGE_GALLERY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FOLDER_NAME + " TEXT,"
                + KEY_URL + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    private void createNoticeTable(SQLiteDatabase db){

        String CREATE_TABLE = "CREATE TABLE " + DT_NOTICE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DATE + " LONG,"
                + KEY_BY + " TEXT,"
                + KEY_URL + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);

    }


    private void createPhirePawaProfileTable(SQLiteDatabase db){

        String CREATE_TABLE = "CREATE TABLE " + DT_PHIRE_PAWA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_BATCH + " INTEGER,"
                + KEY_COMPANY + " TEXT,"
                + KEY_URL + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        switch (oldVersion){
            /*case 1:
                db.execSQL("DROP TABLE IF EXISTS " + DT_SURVEY);
                createTables(DT_SURVEY,db);
            case 2:
                db.execSQL("DROP TABLE IF EXISTS " + DT_QUESTIONS);
                createTables(DT_QUESTIONS,db);
            case 3:
                db.execSQL("DROP TABLE IF EXISTS " + DT_RESPONSES);
                createTables(DT_RESPONSES,db);*/
        }

        /*db.execSQL("DROP TABLE IF EXISTS " + DT_SURVEY);
        db.execSQL("DROP TABLE IF EXISTS " + DT_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + DT_QUESTION_BLOCKS);
        db.execSQL("DROP TABLE IF EXISTS " + DT_QUESTION_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + DT_RESPONSES);
        db.execSQL("DROP TABLE IF EXISTS " + DT_OPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + DT_MATRIX_OPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + DT_SAVED_RESPONSES);*/
        // Create tables again
        //onCreate(db);

    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */


    /*Exporting DB Functions Start*/

    public static boolean createFolder() {

        String myfolder = Environment.getExternalStorageDirectory() + "/" + APP_FOLDER;
        File f = new File(myfolder);
        if (!f.exists()){
            if (!f.mkdir()) {
                Log.e("msg", myfolder + " can't be created.");
                return false;
            } else {
                Log.e("msg", myfolder + " can be created.");
                return true;
            }
        }
        else{
            Log.i("msg", myfolder+" already exits.");
            return true;
        }
    }

    public String getDB() {
        String result = "N";
        boolean isCreated = createFolder();
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.trackbee" +"/databases/"+DATABASE_NAME;
        String backupDBPath = "/" + APP_FOLDER +"/"+DATABASE_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Log.e("Success", "DB Exported!");
            result = "Y";
        } catch(IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*Exporting DB Functions End*/
}