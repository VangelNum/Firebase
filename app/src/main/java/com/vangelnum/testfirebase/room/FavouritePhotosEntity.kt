package com.vangelnum.testfirebase.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "photos_table")
data class FavouritePhotosEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "url") val url: String,
)
