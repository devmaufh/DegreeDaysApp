package com.devmaufh.degreedaysapp.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.devmaufh.degreedaysapp.Entities.DatesEntity;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
@androidx.room.Database(entities = {InsectEntity.class, DatesEntity.class},version = 5, exportSchema = false)
public abstract  class Database  extends RoomDatabase {
    public abstract DaoAccess daoAccess();
    private static volatile  Database INSTANCE;
    static Database getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (Database.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            Database.class,
                            AdditionalMethods.DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback= new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDAsync(INSTANCE).execute();
        }
    };
    private static class PopulateDAsync extends AsyncTask<Void, Void, Void>{
        private final DaoAccess daoAccess;
        private PopulateDAsync(Database db) {
            daoAccess=db.daoAccess();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            ///* TESTING
            //daoAccess.deleteAllInsect();
            //daoAccess.deleteAllDates();
            for(DatesEntity datesEntity: AdditionalMethods.testDatesList()){
                Log.w("DATABASE::DATES INSERTED: ",datesEntity.getDate()+"\n\t\tTMAX: "+datesEntity.getTmax()+"\n\t\t"+datesEntity.getTmin());
              //  daoAccess.insertDate(datesEntity);
            }//*/
            return null;
        }
    }
}
