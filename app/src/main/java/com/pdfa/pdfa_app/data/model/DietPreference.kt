package com.pdfa.pdfa_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "diet_preference",
    foreignKeys = [
        ForeignKey(
            entity = Diet::class,
            parentColumns = ["id"],
            childColumns = ["diet_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DietPreference(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "diet_id", index = true)
    val dietId: Int
)

data class DietPreferenceWithDiet(
    @Embedded
    val dietPreference: DietPreference,

    @Relation(
        parentColumn = "diet_id",
        entityColumn = "id"
    )
    val diet: Diet
)