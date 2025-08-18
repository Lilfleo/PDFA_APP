package com.pdfa.pdfa_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.pdfa.pdfa_app.data.model.Shoplist

//@Dao
//interface ShoplistDao {
//    @Insert
//    suspend fun insertSholist(shoplist: Shoplist)
//
//    @Transaction
//    @Query("SELECT * FROM shoplist")
//    fun getShoplist
//}