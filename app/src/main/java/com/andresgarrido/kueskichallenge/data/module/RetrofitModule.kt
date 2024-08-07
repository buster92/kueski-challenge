package com.andresgarrido.kueskichallenge.data.module

import com.andresgarrido.kueskichallenge.BuildConfig
import com.andresgarrido.kueskichallenge.data.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
//    @Qualifier
//    @Retention(AnnotationRetention.BINARY)
//    annotation class AuthInterceptorOkHttpClient
//
//    @Qualifier
//    @Retention(AnnotationRetention.BINARY)
//    annotation class OtherInterceptorOkHttpClient

    @Provides
    fun provideBaseUrl(): String = "https://api.themoviedb.org/3/"


    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor {
                val newRequest = it.request().newBuilder()
                    .addHeader("Authorization","Bearer ${BuildConfig.API_TOKEN}")
                    .build()
                it.proceed(newRequest)
            }.build())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MovieApiService= retrofit.create(MovieApiService::class.java)

}