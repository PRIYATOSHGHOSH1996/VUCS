package com.vucs.db;

import android.content.Context;
import android.os.AsyncTask;


import com.vucs.R;
import com.vucs.converters.DateTypeConverter;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.vucs.App.getContext;

@Database(entities = {ClaimModel.class, ClaimStatusModel.class, ExpenseCategoryModel.class, ProjectModel.class, ProjectHeadExpenseModel.class, ProjectUserExpenseModel.class, ClaimFileModel.class, UserAllClaim.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    public abstract ClaimDAO claimDAO();
    public abstract ProjectDAO projectDAO();
    public abstract ProjectExpenseDAO projectExpenseDAO();

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
            //new PopulateDbAsync(INSTANCE).execute();
        }

    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ClaimDAO claimDAO;
        private final ProjectDAO projectDAO;
        private final ProjectExpenseDAO projectExpenseDAO;
        PopulateDbAsync(AppDatabase db) {
            claimDAO = db.claimDAO();
            projectDAO = db.projectDAO();
            projectExpenseDAO = db.projectExpenseDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every SetTime.
            // Not needed if you only populate on creation.
/*          projectDAO.deleteAllProjects();
            projectExpenseDAO.deleteAllProjectHeadExpenses();
            projectExpenseDAO.deleteAllProjectUserExpenses();
            claimDAO.deleteAllClaims();

            //Projects
          projectDAO.insertProject(new ProjectModel(1, "Beverage Consumption Survey " ,
                    "Rajat Mishra", "C1005",
                    90000, "MANAGER", 1));
            projectDAO.insertProject(new ProjectModel(2, "Cranberry Consumption Survey ",
                    "Nikhil Sharma", "C1006",
                    95000, "FIELD", 1));
            projectDAO.insertProject(new ProjectModel(3, "Soft Drink Consumption Survey " ,
                    "Aditya Suri", "C1007",
                    100000, "FIELD", 1));

            //Head Expenses
            projectExpenseDAO.insertProjectHeadExpense(
                    new ProjectHeadExpenseModel(1, "Fooding", 10000));
            projectExpenseDAO.insertProjectHeadExpense(
                    new ProjectHeadExpenseModel(1,"Lodging",20000));
            projectExpenseDAO.insertProjectHeadExpense(
                    new ProjectHeadExpenseModel(1,"Travelling",150000));
            projectExpenseDAO.insertProjectHeadExpense(
                    new ProjectHeadExpenseModel(1,"Incentive",5000));

            //User Expenses
            projectExpenseDAO.insertProjectUserExpense(
                    new ProjectUserExpenseModel(1, "Sumukh Dalal", 10000));
            projectExpenseDAO.insertProjectUserExpense(
                    new ProjectUserExpenseModel(1,"Santanu Goswami",20000));
            projectExpenseDAO.insertProjectUserExpense(
                    new ProjectUserExpenseModel(1,"Gautam Mehra",50000));
            projectExpenseDAO.insertProjectUserExpense(
                    new ProjectUserExpenseModel(1,"Mohit Gupta",25000));*/


            //Claim Status
            claimDAO.insertClaimStatus(new ClaimStatusModel(1,"In process"));
            claimDAO.insertClaimStatus(new ClaimStatusModel(2,"Document verified"));
            claimDAO.insertClaimStatus(new ClaimStatusModel(3,"Approved"));
            claimDAO.insertClaimStatus(new ClaimStatusModel(4,"Reimbursed"));
            claimDAO.insertClaimStatus(new ClaimStatusModel(5,"Rejected"));
            claimDAO.insertClaimStatus(new ClaimStatusModel(8,"Wait For Data Upload"));
            claimDAO.insertClaimStatus(new ClaimStatusModel(9,"Wait For File Upload"));

            //Expense Category
           /* claimDAO.insertExpenseCategory(new ExpenseCategoryModel(1,"Food"));
            claimDAO.insertExpenseCategory(new ExpenseCategoryModel(2,"Accomodation"));
            claimDAO.insertExpenseCategory(new ExpenseCategoryModel(3,"Gift"));
            claimDAO.insertExpenseCategory(new ExpenseCategoryModel(4,"Travel"));
            claimDAO.insertExpenseCategory(new ExpenseCategoryModel(5,"Advance"));
*/
            //Claims
/*
            claimDAO.insertClaim(new ClaimModel("1001",1,1,new Date(),50000,"123456.pdf","","Indore Fooding Bill","INVOICE",1,0,0,1));
            claimDAO.insertClaim(new ClaimModel("1002",1,1,new Date(),50000,"123456.pdf","","Indore Lodging Bill","INVOICE",2,0,0,2));
            claimDAO.insertClaim(new ClaimModel("1003",1,1,new Date(),50000,"123456.pdf","","Indore Travelling Bill","INVOICE",3,0,0,3));
            claimDAO.insertClaim(new ClaimModel("1004",1,1,new Date(),50000,"123456.pdf","","Indore Medical Bill","INVOICE",4,0,0,4));
            claimDAO.insertClaim(new ClaimModel("1005",1,1,new Date(),50000,"123456.pdf","","Indore Incentives","VOUCHER",5,0,0,5));
            claimDAO.insertClaim(new ClaimModel("1006",1,1,new Date(),50000,"123456.pdf","","Indore Fooding Bill","INVOICE",1,0,0,1));
            claimDAO.insertClaim(new ClaimModel("1007",1,1,new Date(),50000,"123456.pdf","","Indore Lodging Bill","INVOICE",2,0,0,2));
            claimDAO.insertClaim(new ClaimModel("1008",1,1,new Date(),50000,"123456.pdf","","Indore Travelling Bill","INVOICE",3,0,0,3));
            claimDAO.insertClaim(new ClaimModel("1009",1,1,new Date(),50000,"123456.pdf","","Indore Medical Bill","INVOICE",4,0,0,4));
            claimDAO.insertClaim(new ClaimModel("10010",1,1,new Date(),50000,"123456.pdf","","Indore Incentives","VOUCHER",5,0,0,5));
            claimDAO.insertClaim(new ClaimModel("10011",1,1,new Date(),50000,"123456.pdf","","Indore Fooding Bill","INVOICE",1,0,0,1));
            claimDAO.insertClaim(new ClaimModel("10012",1,1,new Date(),50000,"123456.pdf","","Indore Lodging Bill","INVOICE",2,0,0,2));
            claimDAO.insertClaim(new ClaimModel("10013",1,1,new Date(),50000,"123456.pdf","","Indore Travelling Bill","INVOICE",3,0,0,3));
            claimDAO.insertClaim(new ClaimModel("10014",1,1,new Date(),50000,"123456.pdf","","Indore Medical Bill","INVOICE",4,0,0,4));
            claimDAO.insertClaim(new ClaimModel("10015",1,1,new Date(),50000,"123456.pdf","","Indore Incentives","VOUCHER",5,0,0,5));
            claimDAO.insertClaim(new ClaimModel("10016",1,1,new Date(),50000,"123456.pdf","","Indore Fooding Bill","INVOICE",1,0,0,1));
            claimDAO.insertClaim(new ClaimModel("10017",1,1,new Date(),50000,"123456.pdf","","Indore Lodging Bill","INVOICE",2,0,0,2));
            claimDAO.insertClaim(new ClaimModel("10018",1,1,new Date(),50000,"123456.pdf","","Indore Travelling Bill","INVOICE",3,0,0,3));
            claimDAO.insertClaim(new ClaimModel("10019",1,1,new Date(),50000,"123456.pdf","","Indore Medical Bill","INVOICE",4,0,0,4));
            claimDAO.insertClaim(new ClaimModel("10020",1,1,new Date(),50000,"123456.pdf","","Indore Incentives","VOUCHER",5,0,0,5));
            claimDAO.insertClaim(new ClaimModel("10021",1,1,new Date(),50000,"123456.pdf","","Indore Fooding Bill","INVOICE",1,0,0,1));
            claimDAO.insertClaim(new ClaimModel("10022",1,1,new Date(),50000,"123456.pdf","","Indore Lodging Bill","INVOICE",2,0,0,2));
            claimDAO.insertClaim(new ClaimModel("10023",1,1,new Date(),50000,"123456.pdf","","Indore Travelling Bill","INVOICE",3,0,0,3));
            claimDAO.insertClaim(new ClaimModel("10024",1,1,new Date(),50000,"123456.pdf","","Indore Medical Bill","INVOICE",4,0,0,4));
            claimDAO.insertClaim(new ClaimModel("10025",1,1,new Date(),50000,"123456.pdf","","Indore Incentives","VOUCHER",5,0,0,5));
            claimDAO.insertClaim(new ClaimModel("10026",1,1,new Date(),50000,"123456.pdf","","Indore Fooding Bill","INVOICE",1,0,0,1));
            claimDAO.insertClaim(new ClaimModel("10027",1,1,new Date(),50000,"123456.pdf","","Indore Lodging Bill","INVOICE",2,0,0,2));
            claimDAO.insertClaim(new ClaimModel("10028",1,1,new Date(),50000,"123456.pdf","","Indore Travelling Bill","INVOICE",3,0,0,3));
            claimDAO.insertClaim(new ClaimModel("10029",1,1,new Date(),50000,"123456.pdf","","Indore Medical Bill","INVOICE",4,0,0,4));
            claimDAO.insertClaim(new ClaimModel("10030",1,1,new Date(),50000,"123456.pdf","","Indore Incentives","VOUCHER",5,0,0,5));
*/

            return null;
        }
    }
}
