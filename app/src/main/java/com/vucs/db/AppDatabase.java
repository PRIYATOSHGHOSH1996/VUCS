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
            blogDAO.insertBlog(new BlogModel("This is a Dummy Text To Test The View", "Priyatosh Ghosh",new Date(),"The Election Commission of India (ECI) has told the Supreme Court that electoral bonds, contrary to government claims, wreck transparency in political funding.", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/Reload-1s-200px.gif?alt=media&token=4d011165-81bd-4cd4-83a1-d5412d96bbef"));
            blogDAO.insertBlog(new BlogModel("Electoral bonds hit transparency in political funding, says Election Commission ", "Saikat Ghorai",new Date()," “Even if anything is done, and we are harassed, we will not feel afraid.”\n" +
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
                    "Ms. Vadra also visited the families of CRPF jawans Avadesh Yadav, Vishal Pandey and Ramesh Yadav who were killed in the Pulwama suicide bomb attack.\n", "https://firebasestorage.googleapis.com/v0/b/chattingapp-8dde4.appspot.com/o/DSCN0046.JPG?alt=media&token=039b2e7a-515f-4d44-8a35-406b13272dc2"));
            blogDAO.insertBlog(new BlogModel("Foreign corporate powers can interfere, the poll panel tells Supreme Court.", "Preetam Sarkar",new Date(),"The affidavit extensively quotes from the May 26, 2017 letter the ECI wrote to the Ministry of Law. The letter, annexed with the affidavit, mentions how the amendment in the Companies Act “opens up the possibility of shell companies being set up for the sole purpose of making donations to political parties.”", "default"));
            blogDAO.insertBlog(new BlogModel("EC takes note of NITI Aayog Vice-Chairman's comments on Congress' poll promise ", "Sovon Jana",new Date(),"\n" +
                    "\n" +
                    "The Election Commission has taken cognisance of a statement made by NITI Aayog Vice-Chairman Rajiv Kumar against the Congress’ poll promise of a minimum basic income guarantee of ₹72,000 per year to the poorest families.\n" +
                    "\n" +
                    "“It’s an old pattern followed by Congress. They say & do anything to win elections. Poverty was removed in 1966, One Rank One Pension was later implemented, everyone received proper education under Right of Education! So you see then can say & do anything,” news agency ANI had tweeted quoting Mr. Kumar.\n" +
                    "\n" +
                    "Mr. Kumar also said that in 2008, Union Minister P. Chidambaram increased the fiscal deficit from 2.5% to 6%. “It’s the next step in that pattern. Rahul Gandhi today made the announcement without thinking about its impact on the economy…I think the fiscal deficit may increase from 3.5% to 6%. All the credit rating agencies may bring down our ratings. We may not get loans from outside and eventually, our investments might be blocked,” he said.\n" +
                    "\n" +
                    "Taking note of the matter, the Commission may also seek an explanation from Mr. Kumar.\n", "default"));
            blogDAO.insertBlog(new BlogModel("Politics of publicity is easy: Priyanka Gandhi Vadra tells BJP", "Rohit Singh",new Date()," “Even if anything is done, and we are harassed, we will not feel afraid.”\n" +
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
                    "Ms. Vadra also visited the families of CRPF jawans Avadesh Yadav, Vishal Pandey and Ramesh Yadav who were killed in the Pulwama suicide bomb attack.\n", "default"));

            return null;
        }
    }
}
