package us.kikin.apps.android.bgplayer.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import us.kikin.apps.android.bgplayer.network.NetworkService

@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {

    @Provides
    fun provideNetworkService() : NetworkService {

        return Retrofit.Builder()
            .baseUrl("https://www.giantbomb.com/api/")
            .build()
            .create(NetworkService::class.java)
    }
}
