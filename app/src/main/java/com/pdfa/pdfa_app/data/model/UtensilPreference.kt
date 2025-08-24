package com.pdfa.pdfa_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "utensil_preference",
    foreignKeys = [
        ForeignKey(
            entity = Utensil::class,
            parentColumns = ["id"],
            childColumns = ["utensil_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class UtensilPreference(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "utensil_id", index = true)
    val utensilId: Int
)

data class UtensilPreferenceWithUtensil(
    @Embedded
    val utensilPreference: UtensilPreference,

    @Relation(
        parentColumn = "utensil_id",
        entityColumn = "id"
    )
    val utensil: Utensil
)