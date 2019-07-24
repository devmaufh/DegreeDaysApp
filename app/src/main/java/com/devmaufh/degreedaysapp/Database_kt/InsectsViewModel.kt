package com.devmaufh.degreedaysapp.Database_kt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.devmaufh.degreedaysapp.Entities.IDate

import com.devmaufh.degreedaysapp.Entities.Insect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class InsectsViewModel (application: Application):AndroidViewModel(application){
    private val repository:InsectsRepository
    val allInsects:LiveData<List< Insect >>
    init {
        val insectDao=InsectsRoomDatabase.getDatabase(application,viewModelScope).insectDao()
        repository= InsectsRepository(insectDao)
        allInsects=repository.allInsects
    }
    fun vModelInsects_insert(insect:Insect)=viewModelScope.launch (Dispatchers.IO){
        repository.insert_Insect(insect)
    }
    fun vModelDates_insert(iDate: IDate)=viewModelScope.launch ( Dispatchers.IO ) {
        repository.insert_Date(iDate)
    }
    fun vModelInsects_deleteByModel(insect: Insect)=viewModelScope.launch( Dispatchers.IO ){
        repository.delete_Insect(insect)
    }
    fun vModel_deleteAll()=viewModelScope.launch ( Dispatchers.IO ) {
        repository.delete_allInsects()
        repository.delete_allDates()
    }

}