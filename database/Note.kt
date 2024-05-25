package com.example.submission1.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "news")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "username")
    var username: String = "",
    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,
    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
) : Parcelable