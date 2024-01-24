package com.example.battery.DataBase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "MyEntity")
data class MyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "path") val path: String? = null,
    @ColumnInfo(name = "isChoose") val isChoose: Boolean = false,
    @ColumnInfo(name = "pathSticker") val sticker: String ?= null,
    @ColumnInfo(name = "choosenSticker") val choosenSticker : Boolean = false

) : Parcelable {
    var isSelected: Boolean = false
    var isChoosen: Boolean = false
}