package us.kikin.apps.android.bgplayer.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import us.kikin.apps.android.bgplayer.BuildConfig
import us.kikin.apps.android.bgplayer.network.VideoApi
import us.kikin.apps.android.bgplayer.network.VideoApiHelper
import us.kikin.apps.android.bgplayer.network.VideoApiHelperImpl

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun provideBaseUrl() = "https://www.giantbomb.com/api/"

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
        } else {
            OkHttpClient.Builder()
                .build()
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) =
        retrofit.create(VideoApi::class.java)

    @Provides
    @Singleton
    fun provideVideoHelper(videoHelper: VideoApiHelperImpl): VideoApiHelper =
        videoHelper
}
