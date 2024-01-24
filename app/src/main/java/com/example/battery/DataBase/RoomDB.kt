package com.example.battery.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyEntity::class], version = 2, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun getItemDao(): MyDAO

    companion object {
        @Volatile
        private var instance: RoomDB? = null

        fun getInstance(context: Context): RoomDB {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance1 = Room.databaseBuilder(context.applicationContext, RoomDB::class.java, "MyEntity").build()
                instance = instance1
                return instance1
            }
        }
    }
}