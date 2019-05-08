package com.devmaufh.degreedaysapp.Database;

import android.app.Application;
import android.widget.Toast;

import com.devmaufh.degreedaysapp.Entities.DatesEntity;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DatabaseViewModel extends AndroidViewModel {
    DataRepository repository;
    private LiveData<List<InsectEntity>> mInsects;
    private LiveData<List<DatesEntity>> mDates;

    public DatabaseViewModel(@NonNull Application application) {
        super(application);
        repository= new DataRepository(application);
        mInsects=repository.getInsects();
        mDates=repository.getDates();
    }
    public LiveData<List<DatesEntity>> getmAllDates() {
        return mDates;
    }
    public LiveData<List<InsectEntity>> getmAllInsects() {
        return mInsects;
    }
    public void insertInsect(InsectEntity insect){
        repository.insertInsect(insect);
        Toast.makeText(getApplication().getApplicationContext(), "Data storage succesfull", Toast.LENGTH_SHORT).show();
    }
    public void insertDate(DatesEntity date){
        repository.insertDate(date);
    }
}
