package us.kikin.apps.android.bgplayer.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import us.kikin.apps.android.bgplayer.BuildConfig
import us.kikin.apps.android.bgplayer.network.VideoService

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideBaseUrl() = "https://www.giantbomb.com/api/"

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val originalUrl = original.url

            val url = originalUrl.newBuilder()
                .addQueryParameter(
                    "api_key",
                    BuildConfig.ApiKey
                )
                .addQueryParameter(
                    "format",
                    "json"
                ).build()

            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            clientBuilder
                .addInterceptor(logger)
        }

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): VideoService =
        retrofit.create(VideoService::class.java)
}
