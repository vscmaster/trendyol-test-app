package com.trendyol.vsh.interview.project.core.data.api

import android.util.Log
import com.trendyol.vsh.interview.project.core.data.model.HomeLayout
import com.trendyol.vsh.interview.project.core.data.model.Product
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


private const val BASE_URL =
    "https://browsingpublic.trendyol.com/mobile-zeus-zeus-service/widget/display/"

private const val WIDGET_PAGE_NAME = "interview"
private const val PLATFORM = "android"

internal interface TrendyolService {
    @Headers(
        "User-Agent:android",
        "Build:512",
        "Platform:android",
        "Content-Type:application/json",
    )
    @GET("personalized")
    suspend fun getHomeLayout(
        @Query("widgetPageName") pageName: String = WIDGET_PAGE_NAME,
        @Query("platform") platform: String = PLATFORM
    ): HomeLayout

    @GET
    suspend fun getProductListing(productsUrl: String): List<Product>
}

internal object RetrofitTrendyolApiBuilder {

    private fun getTrendyolService(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: TrendyolService
        get() = getTrendyolService().create(TrendyolService::class.java)
}

internal object KtorTrendyolApiBuilder : TrendyolService {

    private object CustomAndroidHttpLogger : Logger {
        private const val logTag = "TrendyolService"

        override fun log(message: String) {
            Log.i(logTag, message)
        }
    }

    private val client: HttpClient by lazy {
        HttpClient(Android) {
            engine {
                threadsCount = 2
                pipelining = true
            }

            install(Logging) {
                logger = CustomAndroidHttpLogger
                level = LogLevel.INFO
            }

            install(JsonFeature) {
                serializer = GsonSerializer()
            }

            HttpResponseValidator {
                validateResponse { response ->
                    when (response.status.value) {
                        in 300..399 -> Result.failure<Any>(IllegalArgumentException("Redirect error"))
                        in 400..499 -> Result.failure(IllegalArgumentException("Client request error"))
                        in 500..599 -> Result.failure(IllegalArgumentException("Server response error"))
                    }
                }
            }
        }
    }

    val apiService: TrendyolService
        get() = this

    override suspend fun getHomeLayout(pageName: String, platform: String): HomeLayout {
        return client.get("${BASE_URL}personalized") {
            parameter("widgetPageName", "interview")
            parameter("platform", "android")
            headersOf(
                Pair("User-Agent", listOf("android")),
                Pair("Build", listOf("512")),
                Pair("Platform", listOf("android")),
                Pair("Content-Type", listOf("application/json")),
            )
        }
    }

    override suspend fun getProductListing(productsUrl: String): List<Product> {
        return client.get(productsUrl) {
            parameter("Build", "512")
            headersOf(
                Pair("User-Agent", listOf("android")),
                Pair("Build", listOf("512")),
                Pair("Platform", listOf("android")),
                Pair("Content-Type", listOf("application/json")),
            )
        }
    }
}