package com.martianlab.drunkennavigation.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    indices = [Index(value=["run_guid", "text"], unique = false)]
)
data class Point(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    @ColumnInfo(name = "run_guid")
    val guid : String,
    @ColumnInfo(name = "time")
    val time : Long,
    @ColumnInfo(name = "text")
    val text : String,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "user_id")
    val user_id: Int,
    @ColumnInfo(name = "sent")
    var sent: Boolean = false

)