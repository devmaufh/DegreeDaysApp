package com.devmaufh.degreedaysapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import com.devmaufh.degreedaysapp.Entities.DatesEntity;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DataRepository {
    private DaoAccess daoAccess;
    LiveData<List<InsectEntity>> mAllInsects;
    LiveData<List<DatesEntity>> mAllDates;

    DataRepository(Application application){
        Database db=Database.getDatabase(application);
        daoAccess=db.daoAccess();
        mAllInsects=daoAccess.getAllInsects();
        mAllDates=daoAccess.getAllDates();
    }
    LiveData<List<InsectEntity>> getInsects(){
        return mAllInsects;
    }
    LiveData<List<DatesEntity>> getDates(){
        return  mAllDates;
    }
    public void insertInsect(InsectEntity insectEntity){
        new InsertAsyncTaskInsect(daoAccess).execute(insectEntity);
    }
    public void insertDate(DatesEntity datesEntity){
        new InsertAsyncTaskDate(daoAccess).execute(datesEntity);
    }
    private static class InsertAsyncTaskDate extends AsyncTask<DatesEntity,Void,Void> {
        private DaoAccess mAsyncTaskDao;

        InsertAsyncTaskDate(DaoAccess daoAccess){
            mAsyncTaskDao=daoAccess;
        }
        @Override
        protected Void doInBackground(DatesEntity... dates) {
            mAsyncTaskDao.insertDate(dates[0]);
            return null;
        }
    }
    private static class InsertAsyncTaskInsect extends AsyncTask<InsectEntity,Void,Void>{
        private DaoAccess mAsyncTaskDao;

        InsertAsyncTaskInsect(DaoAccess daoAccess){
            mAsyncTaskDao=daoAccess;
        }
        @Override
        protected Void doInBackground(InsectEntity... insectEntities) {
            mAsyncTaskDao.insertInsect(insectEntities[0]);
            return null;
        }
    }

}
