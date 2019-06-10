package com.vucs.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.vucs.R;
import com.vucs.converters.DateTypeConverter;
import com.vucs.dao.BlogDAO;
import com.vucs.dao.EventDAO;
import com.vucs.dao.ImageGalleryDAO;
import com.vucs.dao.JobDAO;
import com.vucs.dao.NoticeDAO;
import com.vucs.dao.PhirePawaProfileDAO;
import com.vucs.model.BlogModel;
import com.vucs.model.CareerModel;
import com.vucs.model.ClassNoticeModel;
import com.vucs.model.EventModel;
import com.vucs.model.ImageGalleryModel;
import com.vucs.model.JobFileModel;
import com.vucs.model.JobModel;
import com.vucs.model.NoticeModel;
import com.vucs.model.UserModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.vucs.App.getContext;

@Database(entities = {BlogModel.class, NoticeModel.class, EventModel.class, ImageGalleryModel.class, JobFileModel.class, JobModel.class, ClassNoticeModel.class, CareerModel.class , UserModel.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every SetTime it is created or opened.
     * <p>
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

    public abstract BlogDAO blogDAO();

    public abstract NoticeDAO noticeDAO();

    public abstract EventDAO eventDAO();

    public abstract ImageGalleryDAO imageGalleryDAO();

    public abstract PhirePawaProfileDAO phirePawaProfileDAO();

    public abstract JobDAO jobDAO();

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BlogDAO blogDAO;
        private final NoticeDAO noticeDAO;
        private final EventDAO eventDAO;
        private final ImageGalleryDAO imageGalleryDAO;
        private final PhirePawaProfileDAO phirePawaProfileDAO;
        private final JobDAO jobDAO;

        PopulateDbAsync(AppDatabase db) {
            blogDAO = db.blogDAO();
            noticeDAO = db.noticeDAO();
            eventDAO = db.eventDAO();
            imageGalleryDAO = db.imageGalleryDAO();
            phirePawaProfileDAO = db.phirePawaProfileDAO();
            jobDAO = db.jobDAO();

        }

        @Override
        protected Void doInBackground(final Void... params) {
            Log.e("app data","database created");

            noticeDAO.insertClassNotice(new ClassNoticeModel("This is a Dummy Text To Test The View", new Date(), "priyatosh ghosh"));
            noticeDAO.insertClassNotice(new ClassNoticeModel("This is a Dummy Text To Test The View", new Date(), "priyatosh ghosh weyuuuuuuuuuuuuuuuuuuuuuuuuugoaiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiirn"));

            jobDAO.insertJob(new JobModel(1, "This is a Dummy Text To Test The View", 1, "Priyatosh Ghosh", new Date(), "The Election Commission of India (ECI) has told the Supreme Court that electoral bonds, contrary to government claims, wreck transparency in political funding.", 1));
            jobDAO.insertJob(new JobModel(2, "This is a Dummy Text To Test The View", 1, "Priyatosh Ghosh", new Date(), "The Election Commission of India (ECI) has told the Supreme Court that electoral bonds, contrary to government claims, wreck transparency in political funding.", 1));
            jobDAO.insertJobFile(new JobFileModel(1, "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l4.jpg?alt=media&token=724fd54b-68ce-4551-af9b-7c4364de32b6"));
            jobDAO.insertJobFile(new JobFileModel(1, "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/g3.jpg?alt=media&token=020a0286-e673-44d2-aa6f-19a68994ebb4"));
            jobDAO.insertJobFile(new JobFileModel(2, "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/sample.pdf?alt=media&token=177abdba-7fd3-4fd2-8c3e-e8a7cbe6b719"));



            phirePawaProfileDAO.insertUsers(new UserModel(11,"Priyatosh","Ghosh","priyatoshghosh26@gmail.com","8768062939","durgapur",2016,2019,"https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l2.jpg?alt=media&token=02d5c867-c5cc-4bad-93e8-8b023cc478fd","Mca",new Date()));
            phirePawaProfileDAO.insertCareer(new CareerModel(1,11,2019,-1,"Frametrics Consulting Pvt.", "Android Developer"));


            phirePawaProfileDAO.insertUsers(new UserModel(12,"Saikat","Ghorai","SaikatGhorai@gmail.com","123456789","mednipur",2015,2018,"https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l2.jpg?alt=media&token=02d5c867-c5cc-4bad-93e8-8b023cc478fd","Msc",new Date()));
            phirePawaProfileDAO.insertCareer(new CareerModel(2,12,2019,-1,"Frametrics Consulting Pvt.", "Web Developer"));


            phirePawaProfileDAO.insertUsers(new UserModel(13,"Rohit","Sing","RohitSing@gmail.com","34353635125","durgapur",2014,2017,"https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l2.jpg?alt=media&token=02d5c867-c5cc-4bad-93e8-8b023cc478fd","Mca",new Date()));
            phirePawaProfileDAO.insertCareer(new CareerModel(3,13,2019,2019,"The AdView.", "Web Developer"));
            phirePawaProfileDAO.insertCareer(new CareerModel(4,13,2018,2019,"Avalgate.   ", "Web Developer"));


            phirePawaProfileDAO.insertUsers(new UserModel(14,"Preetam","Sarkar","PreetamSarkar@gmail.com","132443223","siliguri",2013,2016,"https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l2.jpg?alt=media&token=02d5c867-c5cc-4bad-93e8-8b023cc478fd","Mca",new Date()));
            phirePawaProfileDAO.insertCareer(new CareerModel(5,14,2015,2016,"Avalgate.", "Ios Developer"));
            phirePawaProfileDAO.insertCareer(new CareerModel(6,14,2019,-1,"Avalgate.ffff   ", "Ios Developer"));

            phirePawaProfileDAO.insertUsers(new UserModel(15,"Sovon","Jana","SovonJana@gmail.com","23242421","mednipur",2012,2015,"https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l2.jpg?alt=media&token=02d5c867-c5cc-4bad-93e8-8b023cc478fd","Msc",new Date()));
            phirePawaProfileDAO.insertCareer(new CareerModel(7,15,2016,2018,"Avalgate.", "Web Developer"));
            phirePawaProfileDAO.insertCareer(new CareerModel(8,15,2019,-1,"The AdView.", "Web Developer"));

            phirePawaProfileDAO.insertCareer(new CareerModel(100,1,2016,2019,"Frametrics Consulting Pvt.", "Android Developer"));
            phirePawaProfileDAO.insertCareer(new CareerModel(101,1,2019,-1,"Frametrics Consulting Pvt.", "Android Developer"));
            phirePawaProfileDAO.insertCareer(new CareerModel(102,1,2019,-1,"Frametrics Consulting Pvt.", "Android Developer"));
            phirePawaProfileDAO.insertCareer(new CareerModel(103,1,2019,-1,"Frametrics Consulting Pvt.", "Android Developer"));
            phirePawaProfileDAO.insertCareer(new CareerModel(104,1,2019,-1,"Frametrics Consulting Pvt.", "Android Developer"));
            phirePawaProfileDAO.insertCareer(new CareerModel(105,1,2019,-1,"Frametrics Consulting Pvt.", "Android Developer"));

            Calendar calendar = Calendar.getInstance(Locale.getDefault());

            calendar.set(2019, 01, 22);
            Date date = new Date(calendar.getTimeInMillis());


            eventDAO.insertEvent(new EventModel("phire pawa", "fghujkl;", date));


            imageGalleryDAO.insertImage(new ImageGalleryModel("folder1", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l4.jpg?alt=media&token=724fd54b-68ce-4551-af9b-7c4364de32b6", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l4.jpg?alt=media&token=724fd54b-68ce-4551-af9b-7c4364de32b6"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder1", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder1", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder1", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder1", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder1", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder1", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));
            imageGalleryDAO.insertImage(new ImageGalleryModel("folder2", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720"));


            noticeDAO.insertNotice(new NoticeModel("This is a Dummy Text To Test The View", new Date(), "Priyatosh Ghosh", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l4.jpg?alt=media&token=724fd54b-68ce-4551-af9b-7c4364de32b6"));
            noticeDAO.insertNotice(new NoticeModel("Test 2 ", new Date(), "Priyatosh Ghosh", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/sample.pdf?alt=media&token=177abdba-7fd3-4fd2-8c3e-e8a7cbe6b719"));

            blogDAO.insertBlog(new BlogModel("This is a Dummy Text To Test The View", 1, "Priyatosh Ghosh", new Date(), "The Election Commission of India (ECI) has told the Supreme Court that electoral bonds, contrary to government claims, wreck transparency in political funding.", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l4.jpg?alt=media&token=724fd54b-68ce-4551-af9b-7c4364de32b6", 1));
            blogDAO.insertBlog(new BlogModel("Electoral bonds hit transparency in political funding, says Election Commission ", 1, "Saikat Ghorai", new Date(), " “Even if anything is done, and we are harassed, we will not feel afraid.”\n" +
                    "\n" +
                    "The 2019 Lok Sabha election, especially the contest in Varanasi is “a fight for a new independence,” Congress general secretary Priyanka Gandhi Vadra said on Wednesday, addressing party workers in the constituency held by Prime Minister Narendra Modi.\n" +
                    "\n" +
                    "Ms. Vadra’s address came at the conclusion of her three-day boat tour on the Ganga, from Prayagraj to Varanasi, where she targeted Mr. Modi in a veiled manner.\n" +
                    "\n" +
                    "Listing out the eight cmpaign promises made by the BJP in its manifesto for Varanasi in 2014, Ms. Vadra asked the audience if these had been fulfilled.\n" +
                    "\n" +
                    "‘It is easy to make big promises,” she said. “Politics of publicity is easy,” Mr. Vadra quipped, adding that parties with access to substantial resources found it easy to conduct such politics.\n" +
                    "\n" +
                    "Appealing to Congress workers to ensure their support to whomsoever the party chose as its candidate in Varanasi, Ms. Vadra acknowledged that there were flaws in the party organisation that needed to be corrected. However, the time to bicker among themselves was over, she told the workers.\n" +
                    "\n" +
                    "The Congress leader, who started the final day of her tour from Chunar in Mirzapur, garlanded the statue of former PM Lal Bahadur Shastri enroute in Ramnagar (Varanasi) from where she reached Assi ghat on a boat.\n" +
                    "\n" +
                    "At Assi, Ms. Vadra met a delegation of Jains and also interacted with fisherfolk of the Mallah community. After this, she performed Holika puja at Dashwamedh ghat. She also performed Ganga Arti and puja at the Kashi Vishwanath Temple.\n" +
                    "\n" +
                    "Responding to a reporter’s query about Mr. Modi’s tweet blaming dynasty politics for damaging institutions, Ms. Vadra urged the PM to “stop thinking that people are fools” and “understand that they see through it”.\n" +
                    "\n" +
                    "“The BJP has systematically attacked every institution in this country for the last five years, including the institution of which all of you are a part,” Ms. Vadra asserted during a brief media interaction at Chunar.\n" +
                    "\n" +
                    "Addressing the Mallahs, an OBC community, Ms. Vadra acknowledged their problems with lost pattas (land grants) and informed them that Congress president Rahul Gandhi had aassured that if the party came to power at the Centre, it would set up a new ministry to solve the problems of the fisherfolk.\n" +
                    "\n" +
                    "“You will have to show every leader, especially the arrogant BJP leaders, that you want a new politics, a new government, which makes policies for you and is committed to solving your problems,” Ms. Vadra said.\n" +
                    "\n" +
                    "The meeting was, however, marred by clashes between workers of the party and BJP supporters, which prompted Ms. Vadra to advise the Congress workers to respect dissent. “Our politics is not of assaulting anyone,” she emphasised.\n" +
                    "\n" +
                    "Ms. Vadra also visited the families of CRPF jawans Avadesh Yadav, Vishal Pandey and Ramesh Yadav who were killed in the Pulwama suicide bomb attack.\n", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/top-post1.jpg?alt=media&token=d5bba49a-8e03-47db-a72f-ca941db12720", 1));
            blogDAO.insertBlog(new BlogModel("Foreign corporate powers can interfere, the poll panel tells Supreme Court.", 2, "Preetam Sarkar", new Date(), "The affidavit extensively quotes from the May 26, 2017 letter the ECI wrote to the Ministry of Law. The letter, annexed with the affidavit, mentions how the amendment in the Companies Act “opens up the possibility of shell companies being set up for the sole purpose of making donations to political parties.”", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l2.jpg?alt=media&token=02d5c867-c5cc-4bad-93e8-8b023cc478fd", 1));
            blogDAO.insertBlog(new BlogModel("EC takes note of NITI Aayog Vice-Chairman's comments on Congress' poll promise ", 2, "Sovon Jana", new Date(), "\n" +
                    "\n" +
                    "The Election Commission has taken cognisance of a statement made by NITI Aayog Vice-Chairman Rajiv Kumar against the Congress’ poll promise of a minimum basic income guarantee of ₹72,000 per year to the poorest families.\n" +
                    "\n" +
                    "“It’s an old pattern followed by Congress. They say & do anything to win elections. Poverty was removed in 1966, One Rank One Pension was later implemented, everyone received proper education under Right of Education! So you see then can say & do anything,” news agency ANI had tweeted quoting Mr. Kumar.\n" +
                    "\n" +
                    "Mr. Kumar also said that in 2008, Union Minister P. Chidambaram increased the fiscal deficit from 2.5% to 6%. “It’s the next step in that pattern. Rahul Gandhi today made the announcement without thinking about its impact on the economy…I think the fiscal deficit may increase from 3.5% to 6%. All the credit rating agencies may bring down our ratings. We may not get loans from outside and eventually, our investments might be blocked,” he said.\n" +
                    "\n" +
                    "Taking note of the matter, the Commission may also seek an explanation from Mr. Kumar.\n", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/video-bg.jpg?alt=media&token=d1f837f2-460f-401f-aa03-cb68f4c3d33d", 1));
            blogDAO.insertBlog(new BlogModel("Politics of publicity is easy: Priyanka Gandhi Vadra tells BJP", 3, "Rohit Singh", new Date(), " “Even if anything is done, and we are harassed, we will not feel afraid.”\n" +
                    "\n" +
                    "The 2019 Lok Sabha election, especially the contest in Varanasi is “a fight for a new independence,” Congress general secretary Priyanka Gandhi Vadra said on Wednesday, addressing party workers in the constituency held by Prime Minister Narendra Modi.\n" +
                    "\n" +
                    "Ms. Vadra’s address came at the conclusion of her three-day boat tour on the Ganga, from Prayagraj to Varanasi, where she targeted Mr. Modi in a veiled manner.\n" +
                    "\n" +
                    "Listing out the eight cmpaign promises made by the BJP in its manifesto for Varanasi in 2014, Ms. Vadra asked the audience if these had been fulfilled.\n" +
                    "\n" +
                    "‘It is easy to make big promises,” she said. “Politics of publicity is easy,” Mr. Vadra quipped, adding that parties with access to substantial resources found it easy to conduct such politics.\n" +
                    "\n" +
                    "Appealing to Congress workers to ensure their support to whomsoever the party chose as its candidate in Varanasi, Ms. Vadra acknowledged that there were flaws in the party organisation that needed to be corrected. However, the time to bicker among themselves was over, she told the workers.\n" +
                    "\n" +
                    "The Congress leader, who started the final day of her tour from Chunar in Mirzapur, garlanded the statue of former PM Lal Bahadur Shastri enroute in Ramnagar (Varanasi) from where she reached Assi ghat on a boat.\n" +
                    "\n" +
                    "At Assi, Ms. Vadra met a delegation of Jains and also interacted with fisherfolk of the Mallah community. After this, she performed Holika puja at Dashwamedh ghat. She also performed Ganga Arti and puja at the Kashi Vishwanath Temple.\n" +
                    "\n" +
                    "Responding to a reporter’s query about Mr. Modi’s tweet blaming dynasty politics for damaging institutions, Ms. Vadra urged the PM to “stop thinking that people are fools” and “understand that they see through it”.\n" +
                    "\n" +
                    "“The BJP has systematically attacked every institution in this country for the last five years, including the institution of which all of you are a part,” Ms. Vadra asserted during a brief media interaction at Chunar.\n" +
                    "\n" +
                    "Addressing the Mallahs, an OBC community, Ms. Vadra acknowledged their problems with lost pattas (land grants) and informed them that Congress president Rahul Gandhi had aassured that if the party came to power at the Centre, it would set up a new ministry to solve the problems of the fisherfolk.\n" +
                    "\n" +
                    "“You will have to show every leader, especially the arrogant BJP leaders, that you want a new politics, a new government, which makes policies for you and is committed to solving your problems,” Ms. Vadra said.\n" +
                    "\n" +
                    "The meeting was, however, marred by clashes between workers of the party and BJP supporters, which prompted Ms. Vadra to advise the Congress workers to respect dissent. “Our politics is not of assaulting anyone,” she emphasised.\n" +
                    "\n" +
                    "Ms. Vadra also visited the families of CRPF jawans Avadesh Yadav, Vishal Pandey and Ramesh Yadav who were killed in the Pulwama suicide bomb attack.\n", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/m1.jpg?alt=media&token=cde601d2-5b03-4f5d-8555-31b53a5bd9c8", 1));
            blogDAO.insertBlog(new BlogModel("U.K. parliament to debate Brexit on March 29 as E.U. wants a ‘yes’", 1, "Priyatosh ghosh", new Date(), "The E.U. has agreed to delay Brexit until May 22 if the deal is approved this week.\n" +
                    "The European Union’s executive said on March 28 that if Britain fails to ratify its divorce agreement this week, Brexit will only postponed until April 12 by which time London must inform the bloc of its plans.\n" +
                    "\n" +
                    "European Commission spokesman Margaritis Schinas was commenting on indicative votes in the U.K. parliament that produced no clear majority for any Brexit option: “If the Withdrawal Agreement is not ratified by the end of this week, Article 50 will be extended to April 12 and it is now for the U.K. government to inform about how it sees the next steps,” Mr. Schinas said. “We counted eight ‘noes’ last night, now we need a ‘yes’ on the way forward.”\n" +
                    "\n" +
                    "The E.U. has agreed to delay Brexit until May 22 if the deal is approved this week.\n" +
                    "\n" +
                    "U.K. parliament to debate Brexit on March 29, exact format unclear\n" +
                    "The House of Commons will discuss a motion relating to Brexit on March 29, the government's leader in parliament Andrea Leadsom said on March 28, but it was not yet clear whether this would result in a formal third vote on the government's Brexit deal.\n" +
                    "\n" +
                    "Last week, Speaker, John Bercow, said another vote on the deal could only be held if it was different to the one lawmakers have already rejected.\n" +
                    "\n" +
                    "“We recognise that any motion brought forward tomorrow will need to be compliant with the speakers ruling and that discussion is ongoing and a motion will be tabled just as soon as possible and obviously by later today,” Ms. Leadsom told parliament.\n" +
                    "\n" +
                    "‘May’s Brexit deal is dead’\n" +
                    "Boris Johnson, who led the campaign to leave the European Union, said Ms. May’s twice-defeated Brexit divorce deal is dead, the Evening Standard newspaper said on March 28.\n" +
                    "\n" +
                    "Boris: May’s deal is dead, the newspaper said on its front page.\n" +
                    "\n" +
                    "The newspaper said Mr. Johnson, who fell in behind the deal after Ms. May promised to quit if it was passed, had told friends: “It’s dead anyway.”", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/e2.jpg?alt=media&token=3aa34ca7-bdab-4ed1-bfae-eee339eab06a", 1));
            blogDAO.insertBlog(new BlogModel("IPL 12: Every game is crucial, says Tom Moody", 2, "Saikat Ghosh", new Date(), "Sunrisers Hyderabad head coach Tom Moody said that were hopeful of captain Kane Williamson being available (who missed the first game due to injury) for selection against Rajasthan Royals.\n" +
                    "\n" +
                    "“It is always a joy to play in front of home crowds with the Orange Army supporting us in a big way. There has been dramatic increase in the fan base and this lifts the energy levels of the players,” Moody said. \n" +
                    "\n" +
                    "“We will see how he responds tomorrow (Friday) morning,” he added.\n" +
                    "\n" +
                    "In the pre-match briefing on Thursday, Moody said the pitch looked like a batting surface and that toss may not be critical. “We are happy to set a target or chase one,” he said.\n" +
                    "\n" +
                    "“Royals is a quality side with lot of match-winners like all other teams in the IPL. It is a well-balanced outfit. Every single game is crucial and even if an inch of space is given, any team can lose the contest,” the head coach said.\n" +
                    "\n" +
                    "“We failed in our execution of plans in the closing overs. But, there is no need to read too much into that over when Andre Russell scored those runs in Kolkata. But you should remember we got him cheaply quite a few times earlier,” Moody said.\n" +
                    "\n" +
                    "Collective effort needed\n" +
                    "Royals pacer Jaydev Unadkat said that they were not looking up to only the in-form Jos Buttler. “Yes, anyone can learn watching him play and build partnerships. \n" +
                    "\n" +
                    "“But, we are looking for every individual to contribute. A collective effort is needed,” he said. \n" +
                    "\n" +
                    "“We hope not to make the same mistakes from the game against Kings XI where we saw a collapse once Buttler got out. We should do well tomorrow,” he said.\n" +
                    "\n" +
                    "On the possibility of Rahul Tripathi batting up the order, Unadkat said it depended on what the coach or the captain prefer. “I think they see Rahul as a batter who can play in any position and not necessarily at the top,” he said.\n" +
                    "\n" +
                    "Unadkat said that they have left behind the R. Ashwin episode (running out Jos Buttler) and just wanted to move on. “It didn’t have any impact on the team. It is important to focus on the games ahead,” he said.", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/e3.jpg?alt=media&token=02527ca2-8aea-448a-aeb7-057aa07d3040", 1));
            blogDAO.insertBlog(new BlogModel("Sunrisers Hyderabad head coach Tom Moody said that were hopeful of captain Kane Williamson being available (who missed the first game due to injury) for selection against Rajasthan Royals.\n" +
                    "\n" +
                    "“It is always a joy to play in front of home crowds with the Orange Army supporting us in a big way. There has been dramatic increase in the fan base and this lifts the energy levels of the players,” Moody said. \n" +
                    "\n" +
                    "“We will see how he responds tomorrow (Friday) morning,” he added.\n" +
                    "\n" +
                    "In the pre-match briefing on Thursday, Moody said the pitch looked like a batting surface and that toss may not be critical. “We are happy to set a target or chase one,” he said.\n" +
                    "\n" +
                    "“Royals is a quality side with lot of match-winners like all other teams in the IPL. It is a well-balanced outfit. Every single game is crucial and even if an inch of space is given, any team can lose the contest,” the head coach said.\n" +
                    "\n" +
                    "“We failed in our execution of plans in the closing overs. But, there is no need to read too much into that over when Andre Russell scored those runs in Kolkata. But you should remember we got him cheaply quite a few times earlier,” Moody said.\n" +
                    "\n" +
                    "Collective effort needed\n" +
                    "Royals pacer Jaydev Unadkat said that they were not looking up to only the in-form Jos Buttler. “Yes, anyone can learn watching him play and build partnerships. \n" +
                    "\n" +
                    "“But, we are looking for every individual to contribute. A collective effort is needed,” he said. \n" +
                    "\n" +
                    "“We hope not to make the same mistakes from the game against Kings XI where we saw a collapse once Buttler got out. We should do well tomorrow,” he said.\n" +
                    "\n" +
                    "On the possibility of Rahul Tripathi batting up the order, Unadkat said it depended on what the coach or the captain prefer. “I think they see Rahul as a batter who can play in any position and not necessarily at the top,” he said.\n" +
                    "\n" +
                    "Unadkat said that they have left behind the R. Ashwin episode (running out Jos Buttler) and just wanted to move on. “It didn’t have any impact on the team. It is important to focus on the games ahead,” he said.", 2, "Sovon Jana", new Date(), "Royal Challengers Bangalore opted to field after winning the toss against Mumbai Indians in the second IPL encounter between the two teams.\n" +
                    "\n" +
                    "Jasprit Bumrah, who has been declared fit after sustaining an injury on his left shoulder features in the playing XI with Lasith Malinga straightaway in it.\n" +
                    "\n" +
                    "Malinga replaced Ben Cutting while leg-spinner Mayank Markande replaced teenager Kashmir pacer Rasikh Salam.\n" +
                    "\n" +
                    "Royal Challengers Bangalore:  Virat Kohli (capt), Parthjv Patel (wk), Moeen Ali, AB de Villiers, Shimron Hetmyer, Shivam Dube, Colin de Grandhomme, Navdeep Saini, Yuzvendra Chahal, Umesh Yadav, Mohammed Siraj\n" +
                    "\n" +
                    "Mumbai Indians: Rohit Sharma (c), Quinton de Kock (wk), Suryakumar Yadav, Yuvraj Singh, Kieron Pollard, Hardik Pandya, Krunal Pandya, Mayank Markande, Mitchell McClenaghan, Lasith Malinga, Jasprit Bumrah", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/f1.jpg?alt=media&token=a859166f-679e-4181-b4cc-f266a4bd4be9", 1));
            blogDAO.insertBlog(new BlogModel("Royal Challengers Bangalore opted to field after winning the toss against Mumbai Indians in the second IPL encounter between the two teams.\n" +
                    "\n" +
                    "Jasprit Bumrah, who has been declared fit after sustaining an injury on his left shoulder features in the playing XI with Lasith Malinga straightaway in it.\n" +
                    "\n" +
                    "Malinga replaced Ben Cutting while leg-spinner Mayank Markande replaced teenager Kashmir pacer Rasikh Salam.\n" +
                    "\n" +
                    "Royal Challengers Bangalore:  Virat Kohli (capt), Parthjv Patel (wk), Moeen Ali, AB de Villiers, Shimron Hetmyer, Shivam Dube, Colin de Grandhomme, Navdeep Saini, Yuzvendra Chahal, Umesh Yadav, Mohammed Siraj\n" +
                    "\n" +
                    "Mumbai Indians: Rohit Sharma (c), Quinton de Kock (wk), Suryakumar Yadav, Yuvraj Singh, Kieron Pollard, Hardik Pandya, Krunal Pandya, Mayank Markande, Mitchell McClenaghan, Lasith Malinga, Jasprit Bumrah", 2, "Preetam Sarkar", new Date(), "Martin Guptill is not among the four first-choice overseas players for Sunrisers Hyderabad but the senior New Zealand batsman wants to utilise his time facing Rashid Khan at the nets to prepare for the World Cup game against Afghanistan on June 8.\n" +
                    "\n" +
                    "With David Warner, Jonny Bairstow, Rashid Khan and Shakib Al Hasan being the first choice foreigners and regular captain Kane Williamson waiting to get fit, Guptill will have to bide his time but he wants to make optimal use of it.\n" +
                    "\n" +
                    "For Guptill, Rashid is a bowler he has not faced much save a few games in an earlier Caribbean Premier League edition.\n" +
                    "\n" +
                    "“I played with him in CPL a couple of years back. He is an amazing bowler. He is very hard to face because he is quick,” Guptill said during a media interaction.\n" +
                    "\n" +
                    "“I will be getting into the nets to figure out how to play him because our second game at the World Cup this year is against Afghanistan. Obviously, if I can face him at the nets, may be I can put some plans in place for the World Cup,” said the 32-year-old, who has played 47 Tests, 169 ODIs and 76 T20 Internationals.\n" +
                    "\n" +
                    "Guptill is hopeful of making full use of his chances at Sunrisers, having worked on his technique after an indifferent home series against India.\n" +
                    "\n" +
                    "“During the India series, I had some technical problem and I corrected it against Bangladesh,” he said.\n" +
                    "\n" +
                    "Asked what exactly the problem was, Guptill said: “My balance was not correct. I was not loading up well in my set up that caused me playing to different lines than I would have liked to. But I did work hard with my longtime coach at Auckland with whom I share a good working relationship.”\n" +
                    "\n" +
                    "Guptill has never been a sought after player in IPL like some of his other New Zealand team-mates but he actually has no qualms.\n" +
                    "\n" +
                    "“I go into auctions with reasonably low expectations. If I get picked, it’s good and if not can’t do anything about it. It’s my third season with a third team. So I am doing the rounds. SRH is quite a tight-knit unit and they have had the same core of players for a number of years now. I know I can’t get into the XI straightaway,” he said.\n" +
                    "\n" +
                    "He might get a chance in the second half when Bairstow returns to England but Guptill does not want to look that far ahead.\n" +
                    "\n" +
                    "“I try not to get too ahead. Cricket is a funny game. I take each net session as it comes and work hard.”\n" +
                    "\n" +
                    "Talk about the net session, the best part is getting inputs from someone of VVS Laxman’s calibre.\n" +
                    "\n" +
                    "“Sometimes it’s good to have a fresh pair of eyes looking at your game and see what they pick up. VVS Laxman is an amazing person. It is always good to have players like that around you from whom you can learn a lot. It is quite an exciting prospect to learn from him.”", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/g2.jpg?alt=media&token=16322fdd-ab83-48bd-a6f9-2a59f9b6da6e", 1));
            blogDAO.insertBlog(new BlogModel("Indian Premier League: Want to hold on to my form, says Nitish Rana", 3, "Rohit Sing", new Date(), "Consecutive fifties in different batting positions ought to be a confidence-builder but Kolkata Knight Riders batsman Nitish Rana says he is occupied by the concern of maintaining his form that usually “fizzles” out early in the IPL.\n" +
                    "\n" +
                    "As a makeshift opener in the absence of Sunil Narine, Rana struck a fine 68 in KKR’s first match against Sunrisers Hyderabad on Sunday.\n" +
                    "\n" +
                    "Back at No.4 in their second match, Rana scored a 34-ball 63 against Kings XI Punjab on Wednesday to overtake Delhi Capitals’ Rishabh Pant (103 runs in two matches) in the early Orange Cap race.\n" +
                    "\n" +
                    "“I haven’t thought too far ahead. But for the last couple of seasons, I start well but my form fizzles out towards the later half of the tournament,” Rana said after KKR’s 28-run win here on Wednesday night.\n" +
                    "\n" +
                    "“So this time around, I want to work on this. I want to continue the kind of form I have started in till the end of the tournament.”\n" +
                    "\n" +
                    "Rana did not have a good season with Delhi in the domestic circuit, managing just 147 runs form 10 matches in the Syed Mushtaq Ali T20 and 191 runs from six matches in the Ranji Trophy.\n" +
                    "\n" +
                    "Disturbed by his poor form, the top order KKR batsman spent some time at the Mumbai-based KKR Academy speaking to Abhishek Nayar and Dinesh Karthik.\n" +
                    "\n" +
                    "“I did not work on batting as such but I worked on the mental toughness. KKR academy was quite helpful in that sense because I got one-on-one time with Abhishek bhaiya and DK bhaiya. They helped me clear self doubts. Now, I feel, I’ve become a better player.”\n" +
                    "\n" +
                    "Having impressed at different batting positions, Rana said, “That’s the sign of a good team. My plan was clear — to hit a loose ball and then, let DK (Dinesh Karthik) or (Andre) Russell take over for the last four-five overs.”\n" +
                    "\n" +
                    "Four of his seven sixes came against India’s top off-spinner Ravichandran Ashwin but Rana said the attack was not pre-planned.\n" +
                    "\n" +
                    "“I was just trying to build my innings. I was taking it ball by ball at the start and then when I thought I could charge, I did that.\n" +
                    "\n" +
                    "“It doesn’t matter for me who the bowler is. My game plan was simple. I thought that was the time to attack and I did. That was the plan for the two overs — of Ashwin and South African pacer (Hardus Viljoen).”\n" +
                    "\n" +
                    "Rana also hailed Andre Russell’s 17-ball 48 and said the lifeline, when the Jamaican survived owing to a no ball, gave them 20-25 runs extra.\n" +
                    "\n" +
                    "“Having 218/4 definitely worked in our favour, how Russell got a lifeline and that probably gave us those 20-25 runs extra.\n" +
                    "\n" +
                    "“But even if we had 180 or 200, we would have fought this hard. It wasn’t very easy (to chase) because the ball started seaming in the second innings.”", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/g3.jpg?alt=media&token=020a0286-e673-44d2-aa6f-19a68994ebb4", 1));
            blogDAO.insertBlog(new BlogModel("IPL 2019 | RCB vs MI: Royal Challengers Bangalore’s batting will look to reinvent itself", 3, "Sovon Jana", new Date(), "Bumrah, who hurt his left shoulder in the first match, bowls in the nets\n" +
                    "It was better, Virat Kohli said after Royal Challengers Bangalore’s embarrassing seven-wicket defeat in Chennai, to have got a game like that “out of the way early on”.\n" +
                    "\n" +
                    "It was not possible, Kohli seemed to be suggesting, for all of his batsmen to fail at the same time again. Some may consider such optimism excessive but in its second game at least, against Mumbai Indians here on Thursday, RCB has reason to be confident of a better batting performance.\n" +
                    "\n" +
                    "Batting surface\n" +
                    "The slow turner rolled out at Chepauk was far from conducive to stroke-making, with both sides critical of the pitch afterwards. The surface at the M. Chinnaswamy Stadium, though, is expected to be vastly different.\n" +
                    "\n" +
                    "Kohli's eyes lit up at the toss for India's second T20I against Australia here last month, when he gushed about the pitch resembling the “Bangalore wicket of old”. Last week, Yuzvendra Chahal predicted average scores of “190-200” for the season.\n" +
                    "\n" +
                    "Few excuses\n" +
                    "For all the challenges the pitch in Chennai posed, RCB can have few excuses after getting knocked over for 70. Any little encouragement has to be derived from the team’s effort in the field, when it took the game as deep as the 18th over.\n" +
                    "\n" +
                    "Kohli will not have learnt much from that contest; it remains to be seen if he persists with the same combination or gives the likes of Washington Sundar a chance.\n" +
                    "\n" +
                    "RCB will be wary of Mumbai, which also began the season with defeat. Delhi Capitals’ Rishabh Pant was unstoppable that evening but there were positive signs for Rohit Sharma’s side. The captain and Quinton de Kock managed a good start.\n" +
                    "\n" +
                    "Yuvraj Singh, who had made only one score over 50 for Punjab (across formats) this domestic season, struck a 35-ball-53. Kieron Pollard and Krunal Pandya also made a mark.\n" +
                    "\n" +
                    "Good signs\n" +
                    "Jasprit Bumrah, who injured his left shoulder during the game, bowled in the nets without any visible discomfort on Wednesday. He would be assessed afterwards, a Mumbai official stated, but the signs are good.\n" +
                    "\n" +
                    "Lasith Malinga, who has been cleared to take part in the IPL by Sri Lanka Cricket, was expected to land late in the evening.\n" +
                    "\n" +
                    "If the pitch behaves like it is expected to, the crowds will be greatly entertained. As they await a first home game of the season, the RCB faithful will hope — like every year — that this time the trophy is theirs.", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l1.jpg?alt=media&token=ef5113c7-92f2-489b-827c-c06f087bde9b", 1));
            blogDAO.insertBlog(new BlogModel("Knight Riders’ three musketeers set up a crushing win", 3, "Rohit Sing", new Date(), "Rana, Uthappa and Russell take the game away from Kings XI Punjab with a mix of audacious and cautious batting\n" +
                    "Ruthless bludgeoning of the cricket ball by its three reliable musketeers — Nitish Rana, Robin Uthappa and Andre Russell — powered Kolkata Knight Riders to a crushing 28-run win over Kings XI Punjab in the Indian Premier League at the Eden Gardens here on Wednesday.\n" +
                    "\n" +
                    "The trio’s fireworks spurred KKR to 218 for four, the highest total at the venue, and provided thorough entertainment to its 51,000 passionate supporters. In reply, KXIP managed 190 for four as KKR scored its second consecutive win at home before setting off on the road for its next encounter.\n" +
                    "\n" +
                    "Mystery unravelled\n" +
                    "Sunil Narine returned to his favourite opening slot and unravelled the ‘mystery’ around KXIP’s costly buy — Varun Chakaravarthy. The spinner conceded 25 runs — including three sixes and a four — in his first over on debut to fetch KKR some early momentum.\n" +
                    "\n" +
                    "After Chris Lynn — his ill-timed hoick off Mohd. Shami was well held by David Miller — and Narine fell in similar fashion, Uthappa (67 not out off 50) and Rana (63 off 34) took charge.\n" +
                    "\n" +
                    "It was one of Uthappa’s finest IPL knocks as he blended aggression with caution. Rana, encouraged by his previous innings, stretched his legs and swung his arms to thrash the KXIP bowlers and help KKR maintain an eight-plus run rate.\n" +
                    "\n" +
                    "Rana grabbed 50 through fours and sixes alone. No matter how he held the bat, the Delhi batsman hit all his seven sixes, including four off R. Ashwin, in the ‘V’ and left the KXIP skipper clueless.\n" +
                    "\n" +
                    "Uthappa enjoyed his timing and placement as he cut and flicked to gather most of his runs. He gave perfect company to Rana by producing a fine mix of ones and twos interspersed with fours and sixes.", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/l2.jpg?alt=media&token=02d5c867-c5cc-4bad-93e8-8b023cc478fd", 1));
            return null;
        }
    }
}
