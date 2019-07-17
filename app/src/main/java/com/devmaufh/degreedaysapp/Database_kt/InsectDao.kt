package com.devmaufh.degreedaysapp.Database_kt

import androidx.lifecycle.LiveData
import androidx.room.*
import com.devmaufh.degreedaysapp.Entities.IDate
import com.devmaufh.degreedaysapp.Entities.Insect

@Dao
interface InsectDao {
    //Insectos
    @Query("SELECT * FROM insects_table ORDER BY registration_date")
    fun Insects_selectAll():LiveData<List<Insect>>

    @Query("SELECT * FROM insects_table WHERE insect_id=:insect_id")
    fun Insects_selectById(insect_id:Int):Insect

    @Delete()
    fun Insects_deleteByModel(insect: Insect)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insects_insert(insect: Insect)



    //Fechas
    @Query("SELECT * FROM dates_table ORDER BY date_id")
    fun Dates_selectAll():LiveData<List<IDate>>

    @Query("SELECT * FROM dates_table WHERE date_id=:date_id")
    fun Dates_selectById(date_id:Int):IDate

    @Delete()
    fun Dates_deleteByModel(date:IDate)

    @Insert
    suspend fun Dates_insert(date: IDate)
}