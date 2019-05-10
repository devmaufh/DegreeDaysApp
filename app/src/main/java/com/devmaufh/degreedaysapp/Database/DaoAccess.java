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

    @Query("SELECT * FROM insects")
    LiveData<List<InsectEntity>> getAllInsects();
    @Query("SELECT * FROM insects WHERE id=:idInsect")
    InsectEntity selectInsectWhere(int idInsect);


    //Dates methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDate(DatesEntity dateE);

    @Update
    void updateInsect(DatesEntity datesE);

    @Delete
    void deleteDates(DatesEntity datesE);

    @Query("SELECT * FROM dates")
    LiveData<List<DatesEntity>>  getAllDates();

    @Query("SELECT * FROM dates WHERE id=:idDate")
    DatesEntity selectDateById(int idDate);

    @Query("SELECT COUNT(*) FROM dates WHERE date=:da")
    int selectDateByDate(String da);

    @Query("SELECT * FROM dates WHERE date=:da")
    DatesEntity getDateByDate(String da);

    @Query("DELETE FROM dates")
    void deleteAllDates();
}
