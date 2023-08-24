package com.example.storyapp.presentation.data.source.Api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuild (private val dataStore: DataStore<Preferences>) {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .addInterceptor(AuthInterceptor(dataStore))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiServices = getRetrofit().create(ApiServices::class.java)

}