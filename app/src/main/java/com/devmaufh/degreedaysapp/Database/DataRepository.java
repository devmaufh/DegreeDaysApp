package com.devmaufh.degreedaysapp.Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.devmaufh.degreedaysapp.Entities.DatesEntity;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.R;

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
    public void getDateByDate2(String date, GetDateByDate.AsyncDate asyncDate){
        new GetDateByDate(daoAccess,asyncDate,date).execute();
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
            Log.w("AsyncSELECTDATEBY",daoAccess.selectDateByDate(date)+"");
            //result.ResInt(daoAccess.selectDateByDate(date));
            return  daoAccess.selectDateByDate(date);
        }
        @Override
        protected void onPostExecute(Integer integer) {
            Log.w("AsyncSELECTDATEBY",integer+"\t");
            if(delegate==null){
                Log.w("AsyncSELECTDATEBY","INTERFAZ NULA");
            }else{
                Log.w("AsyncSELECTDATEBY","INTERFAZ COOL");

                delegate.response(integer);
            }
        }
    }
    public static class GetDateByDate extends AsyncTask<Void,Void,DatesEntity>{
        private DaoAccess dao;
        private String date;
        public AsyncDate asyncDate;

        public interface AsyncDate{
            void response(DatesEntity entity);
            void error(String error);
        }
        public GetDateByDate(DaoAccess dao,AsyncDate asyncDate,String date){
            this.dao=dao;
            this.date=date;
            this.asyncDate=asyncDate;

        }

        @Override
        protected DatesEntity doInBackground(Void... voids) {
            return  dao.getDateByDate(date);
        }

        @Override
        protected void onPostExecute(DatesEntity datesEntity) {
            super.onPostExecute(datesEntity);
            if(datesEntity==null){
                asyncDate.error("No se encontraron registros de temperatura para esta fecha");
            }else{ Log.w("ASYNCDATE","ID: "+datesEntity.getId());
                Log.w("ASYNCDATE","DATE: "+datesEntity.getDate());

                asyncDate.response(datesEntity);
            }
        }
    }
}
