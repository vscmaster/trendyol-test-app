package com.trendyol.vsh.interview.project.core.data.room

import androidx.room.*
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem

@Dao
abstract class ProductEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: ProductItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(vararg entity: ProductItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<ProductItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: ProductItem)

    @Delete
    abstract suspend fun delete(entity: ProductItem): Int

    @Query(
        """
        SELECT * FROM products WHERE id = :id
        """
    )
    abstract suspend fun product(id: Int): ProductItem
}
