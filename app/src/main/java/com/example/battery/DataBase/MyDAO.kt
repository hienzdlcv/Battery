package com.example.battery.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MyEntity) : Long

    @Query("Select * From MyEntity")
    fun getAll(): LiveData<List<MyEntity>>

    @Delete
    fun deleteSelectedItem(entity : List<MyEntity>)

    @Delete
    fun deleteChoosenSticker(entityPath: List<MyEntity>)

    @Query("UPDATE myentity SET isChoose = CASE WHEN path = :entityPath THEN 1 ELSE 0 END")
    suspend fun updateIsChoosen(entityPath: String)

    @Query("UPDATE myentity SET choosenSticker = CASE WHEN pathSticker = :entityPath THEN 1 ELSE 0 END")
    suspend fun updateisChoosenSticker(entityPath: String)

    @Query("SELECT path FROM MyEntity WHERE isChoose = 1 LIMIT 1")
    suspend fun theChoosenOne() : String?

    @Query("SELECT pathSticker FROM MyEntity WHERE choosenSticker = 1 LIMIT 1")
    suspend fun theChoosenSticker() : String?

    @Query("SELECT * FROM MyEntity WHERE pathSticker IS NOT NULL")
    fun getAllSticker() : LiveData<List<MyEntity>>

    @Query("SELECT * FROM MyEntity WHERE path IS NOT NULL")
    fun getAll1() : LiveData<List<MyEntity>>
}