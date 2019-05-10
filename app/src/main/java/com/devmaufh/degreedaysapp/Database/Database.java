package com.devmaufh.degreedaysapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.devmaufh.degreedaysapp.Entities.DatesEntity;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {InsectEntity.class, DatesEntity.class},version = 3, exportSchema = false)
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
        }
    };
    private static class PopulateDAsync extends AsyncTask<Void, Void, Void>{
        private final DaoAccess daoAccess;
        private PopulateDAsync(Database db) {
            daoAccess=db.daoAccess();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
