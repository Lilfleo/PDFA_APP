package com.pdfa.pdfa_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "tag_preference",
    foreignKeys = [
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class TagPreference(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "tag_id", index = true)
    val tagId: Int
)

data class TagPreferenceWithTag(
    @Embedded
    val tagPreference: TagPreference,

    @Relation(
        parentColumn = "tag_id",
        entityColumn = "id"
    )
    val tag: Tag
)