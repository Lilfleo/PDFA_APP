package com.pdfa.pdfa_app.data.database

import androidx.room.TypeConverter
import com.pdfa.pdfa_app.api.Ingredient
import com.pdfa.pdfa_app.api.Tags
import kotlinx.serialization.json.Json
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromIngredientsList(ingredients: List<Ingredient>): String {
        return Json.encodeToString(ingredients)
    }

    @TypeConverter
    fun toIngredientsList(ingredientsString: String): List<Ingredient> {
        return Json.decodeFromString(ingredientsString)
    }

    @TypeConverter
    fun fromTags(tags: Tags): String {
        return Json.encodeToString(tags)
    }

    @TypeConverter
    fun toTags(tagsString: String): Tags {
        return Json.decodeFromString(tagsString)
    }

    @TypeConverter
    fun fromStringList(strings: List<String>): String {
        return Json.encodeToString(strings)
    }

    @TypeConverter
    fun toStringList(stringsString: String): List<String> {
        return Json.decodeFromString(stringsString)
    }
}