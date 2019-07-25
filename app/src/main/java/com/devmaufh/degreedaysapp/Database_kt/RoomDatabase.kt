package com.devmaufh.degreedaysapp.Database_kt

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.devmaufh.degreedaysapp.Entities.IDate
import com.devmaufh.degreedaysapp.Entities.Insect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Insect::class,IDate::class],version = 1, exportSchema = false)
public abstract class InsectsRoomDatabase:RoomDatabase(){

    abstract fun insectDao():InsectDao

    companion object{
        @Volatile
        private var INSTANCE:InsectsRoomDatabase?=null

        fun getDatabase(context:Context,scope: CoroutineScope):InsectsRoomDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                        context.applicationContext,
                        InsectsRoomDatabase::class.java,
                        "Insects_database"
                )
                        .addCallback(InsectDatabaseCallback(scope))
                        .build()
                INSTANCE=instance
                return instance
            }
        }
        private class InsectDatabaseCallback(
                private val scope:CoroutineScope
        ):RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { insectsRoomDatabase ->
                    scope.launch (Dispatchers.IO) {
                        populateDatabase(insectsRoomDatabase.insectDao())
                    }
                }
            }
        }
        suspend fun populateDatabase(insectDao: InsectDao){
        }
    }
}
