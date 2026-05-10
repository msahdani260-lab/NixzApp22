package com.futuristic.app.data.remote

import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.GET

// --- Models ---
@Serializable
data class DashboardResponse(
    val status: String,
    val items: List<DashboardItem>
)

@Serializable
data class DashboardItem(
    val id: Int,
    val title: String,
    val value: String
)

// --- API Interface ---
interface ApiService {
    @GET("dashboard/data")
    suspend fun getDashboardData(): DashboardResponse
}

// --- API Manager / Client ---
object ApiClient {
    private const val BASE_URL = "https://mockapi.example.com/" // Fake API

    private val json = Json { ignoreUnknownKeys = true }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}
