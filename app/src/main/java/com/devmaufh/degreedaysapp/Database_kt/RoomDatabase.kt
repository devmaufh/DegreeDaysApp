package com.devmaufh.degreedaysapp.Database_kt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmaufh.degreedaysapp.Entities.IDate
import com.devmaufh.degreedaysapp.Entities.Insect
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Insect::class,IDate::class],version = 1, exportSchema = false)
public abstract class InsectsRoomDatabase:RoomDatabase(){

    abstract fun insectDao():InsectDao

    companion object{
        @Volatile
        private var INSTANCE:InsectsRoomDatabase?=null

        fun getDatabase(context:Context):InsectsRoomDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                        context.applicationContext,
                        InsectsRoomDatabase::class.java,
                        "Insects_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }

}