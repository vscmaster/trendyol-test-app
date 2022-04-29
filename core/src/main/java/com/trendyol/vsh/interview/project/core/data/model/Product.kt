package com.trendyol.vsh.interview.project.core.data.model

import com.google.gson.annotations.SerializedName

internal data class Product(
    @SerializedName("boutiqueId") val boutiqueId: Int? = null,
    @SerializedName("brandName") val brandName: String? = null,
    @SerializedName("brandId") val brandId: Int? = null,
    @SerializedName("businessUnit") val businessUnit: String? = null,
    @SerializedName("categoryId") val categoryId: Int? = null,
    @SerializedName("categoryHierarchy") val categoryHierarchy: String? = null,
    @SerializedName("categoryName") val categoryName: String? = null,
    @SerializedName("contentId") val contentId: Int? = null,
    @SerializedName("id") val id: Int,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    @SerializedName("imageUrls") val imageUrls: ArrayList<String> = arrayListOf(),
    @SerializedName("mainId") val mainId: Int? = null,
    @SerializedName("marketPrice") val marketPrice: Double? = null,
    @SerializedName("name") val name: String,
    @SerializedName("salePrice") val salePrice: Double? = null,
    @SerializedName("mOriginalPrice") val mOriginalPrice: Double? = null,
    @SerializedName("stockStatus") val stockStatus: Int? = null,
    @SerializedName("promotionMessage") val promotionMessage: String? = null,
    @SerializedName("merchantId") val merchantId: Int? = null,
    @SerializedName("averageRating") val averageRating: Double = 0.0,
    @SerializedName("ratingCount") val ratingCount: Int = 0,
    @SerializedName("discountedPrice") val discountedPrice: Double? = null,
    @SerializedName("newDiscountedPrice") val newDiscountedPrice: Double? = null,
)