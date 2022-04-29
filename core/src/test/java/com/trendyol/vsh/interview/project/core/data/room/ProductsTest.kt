package com.trendyol.vsh.interview.project.core.data.room

import com.trendyol.vsh.interview.project.core.data.insertProduct
import com.trendyol.vsh.interview.project.core.data.productItem1
import com.trendyol.vsh.interview.project.core.di.module.DatabaseModule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

@UninstallModules(DatabaseModule::class)
@HiltAndroidTest
class ProductsTest : DatabaseTestRules() {
    @Inject
    internal lateinit var database: TrendyolDatabase

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            insertProduct(database)
        }
    }

    @Test
    fun insert() = runTest {
        val dao = database.productsDao()
        dao.insert(productItem1)
        assertThat(dao.product(productItem1.id), `is`(productItem1))
    }

    @Test
    fun insert_withSameId() = runTest {
        val dao = database.productsDao()
        dao.insert(productItem1)

        val copy = productItem1.copy(id = 3773)
        dao.insert(copy)

        assertThat(dao.product(3773), `is`(copy))
    }

    @Test
    fun delete() = runTest {
        val dao = database.productsDao()
        dao.insert(productItem1)
        dao.delete(productItem1)
        assertThat(dao.product(productItem1.id), `is`(nullValue()))
    }
}
