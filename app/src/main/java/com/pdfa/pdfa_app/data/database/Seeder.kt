package com.pdfa.pdfa_app.data.database

import android.content.Context
// si fichier json dans assets
import com.pdfa.pdfa_app.data.model.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
// pour parser json
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale
import com.pdfa.pdfa_app.data.database.AppDatabase

class Seeder(private val context: Context, private val db: AppDatabase) {

    fun seedFoodsFromJson() {
        CoroutineScope(Dispatchers.IO).launch {
            val jsonString = loadJSONFromAsset("foods.json")
            if (jsonString != null) {
                val jsonArray = JSONArray(jsonString)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("name")
                    val link = jsonObject.getString("link")

                    val caloriesPerKg = if (jsonObject.has("caloriesPerKg")) {
                        jsonObject.getInt("caloriesPerKg")
                    } else {
                        0
                    }

                    val caloriesPerUnit = if (jsonObject.has("caloriesPerUnit")) {
                        jsonObject.getInt("caloriesPerUnit")
                    } else {
                        0
                    }

                    val expirationTimeStr = jsonObject.getString("expiration_time")
                    val expirationTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                        .parse(expirationTimeStr)

                    val food = Food(
                        id = id,
                        name = name,
                        link = link,
                        caloriesPerKg = caloriesPerKg,
                        caloriesPerUnit = caloriesPerUnit,
                        expirationTime = expirationTime ?: java.util.Date()
                    )

                    db.foodDao().insertFood(food)
                }
            }
        }
    }

    private fun loadJSONFromAsset(filename: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}
