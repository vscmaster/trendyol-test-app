package com.trendyol.vsh.interview.project.core.ui.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    indices = [
        Index("id", unique = true)
    ]
)
data class ProductItem(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "boutiqueId") val boutiqueId: Int? = null,
    @ColumnInfo(name = "brandName") val brandName: String? = null,
    @ColumnInfo(name = "brandId") val brandId: Int? = null,
    @ColumnInfo(name = "businessUnit") val businessUnit: String? = null,
    @ColumnInfo(name = "categoryId") val categoryId: Int? = null,
    @ColumnInfo(name = "categoryHierarchy") val categoryHierarchy: String? = null,
    @ColumnInfo(name = "categoryName") val categoryName: String? = null,
    @ColumnInfo(name = "contentId") val contentId: Int? = null,
    @ColumnInfo(name = "imageUrl") val imageUrl: String? = null,
    @ColumnInfo(name = "imageUrls") val imageUrls: List<String> = arrayListOf(),
    @ColumnInfo(name = "mainId") val mainId: Int? = null,
    @ColumnInfo(name = "marketPrice") val marketPrice: String? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "salePrice") val salePrice: String? = null,
    @ColumnInfo(name = "originalPrice") val originalPrice: String? = null,
    @ColumnInfo(name = "stockStatus") val stockStatus: Int? = null,
    @ColumnInfo(name = "promotionMessage") val promotionMessage: String? = null,
    @ColumnInfo(name = "merchantId") val merchantId: Int? = null,
    @ColumnInfo(name = "averageRating") val averageRating: Double,
    @ColumnInfo(name = "ratingCount") val ratingCount: Int,
    @ColumnInfo(name = "discountedPrice") val discountedPrice: String? = null,
    @ColumnInfo(name = "newDiscountedPrice") val newDiscountedPrice: String? = null,
)