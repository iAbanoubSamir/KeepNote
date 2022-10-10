package com.abanoub.samir.keep.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abanoub.samir.keep.utils.Constants.TASKS_TABLE_NAME
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = TASKS_TABLE_NAME)
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val important: Boolean = false,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable {
    val createdDateFormatter: String
        get() = DateFormat.getDateTimeInstance().format(createdAt)
}
