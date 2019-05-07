package com.devmaufh.degreedaysapp.Database;

import com.devmaufh.degreedaysapp.Entities.DatesEntity;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DaoAccess {
    //Insects methods
    @Insert
    void insertInsect(InsectEntity insectE);
    @Update
    void updateInsect(InsectEntity insectE);
    @Delete
    void deleteInsect(InsectEntity insectE);
    @Query("SELECT * FROM insects ORDER BY id")
    LiveData<List<InsectEntity>> getAllInsects();

    //Dates methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDate(DatesEntity dateE);
    @Update
    void updateInsect(DatesEntity datesE);
    @Delete
    void deleteDates(DatesEntity datesE);
    @Query("SELECT * FROM dates")
    LiveData<List<DatesEntity>>  getAllDates();
}
