package com.vucs.db;

import android.content.Context;
import android.os.AsyncTask;


import com.vucs.model.BlogModel;
import com.vucs.R;
import com.vucs.converters.DateTypeConverter;
import com.vucs.dao.BlogDAO;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.vucs.App.getContext;

@Database(entities = {BlogModel.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    public abstract BlogDAO blogDAO();


    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, getContext().getString(R.string.database_name))
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every SetTime it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st SetTime,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            //new PopulateDbAsync(INSTANCE).execute();
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
        }

    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BlogDAO blogDAO;
        PopulateDbAsync(AppDatabase db) {
            blogDAO = db.blogDAO();

        }

        @Override
        protected Void doInBackground(final Void... params) {
            blogDAO.insertBlog(new BlogModel("a", "me",new Date(),"sfcbsadjkfcgeiysfugbweljkfbviusfgiuwegjc"));
            blogDAO.insertBlog(new BlogModel("b", "Saikat",new Date(),"sfcbjhklllllllllllllllllllll.hgtydrsysdxjyjdtyudysadjkfcgeiysfugbweljkfbviusfgiuwegjc"));
            blogDAO.insertBlog(new BlogModel("c", "Preetam",new Date(),"sfcbsadjkfcgeiysfugbweljkfbviusfgiuwegjc"));
            blogDAO.insertBlog(new BlogModel("d", "Sovon",new Date(),"sfcbsadjkfcgeiysfugbweljytdfytsusdtrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssskfbviusfgiuwegjc"));
            blogDAO.insertBlog(new BlogModel("e", "Rohit",new Date(),"sfcbsadjkfcgeiysfugbweljkfbviusfgiuwegjc"));

            return null;
        }
    }
}
