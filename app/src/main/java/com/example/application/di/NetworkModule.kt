package com.example.application.di

import android.content.Context
import com.example.application.BuildConfig
import com.example.application.data.apiServices.MovieApiServices
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        @ApplicationContext application: Context,
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .client(OkHttpClient().newBuilder().apply {

                callTimeout(40, TimeUnit.SECONDS)
                connectTimeout(40, TimeUnit.SECONDS)
                readTimeout(40, TimeUnit.SECONDS)
                writeTimeout(40, TimeUnit.SECONDS)
                addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                cache(Cache(File(application.cacheDir, "http-cache"), 10L * 1024 * 1024))
                retryOnConnectionFailure(true)
                addInterceptor(Interceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder()
                            .header("Cache-Control", "public, max-age=" + 60).build()
                    )
                })
            }.build())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(object : Any() {
                            @ToJson
                            fun toJson(writer: JsonWriter, o: Nothing?) {
                                writer.nullValue()
                                Timber.d(o.toString())
                            }

                            @FromJson
                            fun fromJson(reader: JsonReader): Nothing? {
                                reader.skipValue()
                                return null
                            }
                        })
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
    }

    @Singleton
    @Provides
    fun provideMovieApiServices(retrofit: Retrofit.Builder): MovieApiServices =
        retrofit.baseUrl(BuildConfig.API_BASE_URL)
            .build().create((MovieApiServices::class.java))
}