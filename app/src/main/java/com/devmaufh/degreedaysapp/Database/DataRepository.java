package com.devmaufh.degreedaysapp.Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

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
    public void getDateByDate(String date, SelectDateByDate.AsyncResponse response){
        new SelectDateByDate(daoAccess,response,date).execute("");
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
    public static class SelectDateByDate extends  AsyncTask<String,Void,Integer>{
        private DaoAccess daoAccess;
        private String date;
        public AsyncResponse delegate;

        public interface AsyncResponse{
            void response(int output);
        }
        public SelectDateByDate(DaoAccess daoAccess,AsyncResponse delegate,String date){
            this.daoAccess=daoAccess;
            this.date=date;
            this.delegate=delegate;
        }
        @Override
        protected Integer doInBackground(String... strings) {
            Log.w("BACK",daoAccess.selectDateByDate(date)+"");
            //result.ResInt(daoAccess.selectDateByDate(date));
            return  daoAccess.selectDateByDate(date);
        }
        @Override
        protected void onPostExecute(Integer integer) {
            Log.w("BACK 1",integer+"\t");
            if(delegate==null){
                Log.w("MSG","INTERFAZ NULA");
            }else{
                Log.w("MSG","INTERFAZ COOL");

                delegate.response(integer);
            }
        }
    }
}
