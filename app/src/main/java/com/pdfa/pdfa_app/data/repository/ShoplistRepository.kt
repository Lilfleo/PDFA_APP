package com.pdfa.pdfa_app.data.repository

import com.pdfa.pdfa_app.data.dao.ShoplistDao
import com.pdfa.pdfa_app.data.model.Shoplist
import com.pdfa.pdfa_app.data.model.ShoplistWithFood
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoplistRepository @Inject constructor(
    private val dao: ShoplistDao
) {
    val allFoodDetail : Flow<List<ShoplistWithFood>> = dao.getShoplist()

    suspend fun insertShoplist(shoplist: Shoplist) {
        dao.insertSholist(shoplist)
    }

    suspend fun updateShoplist(shoplist: Shoplist) {
        dao.updateShoplist(shoplist)
    }

    suspend fun deleteShoplist(shoplist: Shoplist) {
        dao.deleteShoplist(shoplist)
    }

    suspend fun findByFoodId(foodId: Int): Shoplist? = dao.findByFoodId(foodId)
}