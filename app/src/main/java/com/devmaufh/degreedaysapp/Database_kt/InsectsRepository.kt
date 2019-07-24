package com.devmaufh.degreedaysapp.Database_kt

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.devmaufh.degreedaysapp.Entities.IDate
import com.devmaufh.degreedaysapp.Entities.Insect

class InsectsRepository(private val insectDao:InsectDao){
    val allInsects:LiveData<List< Insect >> = insectDao.Insects_selectAll()
    val allDates:LiveData<List<IDate>> = insectDao.Dates_selectAll();

    @WorkerThread
    suspend fun  insert_Insect(insect: Insect){
        insectDao.Insects_insert(insect)
    }

    @WorkerThread
    suspend fun delete_Insect(insect:Insect){
        insectDao.Insects_deleteByModel(insect)
    }

    @WorkerThread
    suspend fun delete_allInsects(){
        insectDao.delete_allInsects()
    }
    @WorkerThread
    suspend fun insert_Date(iDate: IDate){
        insectDao.Dates_insert(iDate)
    }
     @WorkerThread
     suspend fun delete_allDates(){
         insectDao.delete_allDates()
     }
}