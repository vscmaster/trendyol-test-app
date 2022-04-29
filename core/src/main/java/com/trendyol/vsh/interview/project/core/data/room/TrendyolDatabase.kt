package com.trendyol.vsh.interview.project.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem

@Database(
    entities = [
        ProductItem::class,
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
internal abstract class TrendyolDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductEntryDao
}
