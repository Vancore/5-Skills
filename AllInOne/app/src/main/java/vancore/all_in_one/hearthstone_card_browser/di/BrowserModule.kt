package vancore.all_in_one.hearthstone_card_browser.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vancore.all_in_one.hearthstone_card_browser.data.BrowserLocalRepository
import vancore.all_in_one.hearthstone_card_browser.data.BrowserRemoteRepository
import vancore.all_in_one.hearthstone_card_browser.data.BrowserRepository
import vancore.all_in_one.hearthstone_card_browser.data.BrowserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BrowserModule {

    @Provides
    @Singleton
    fun provideRemoteDatabase(@ApplicationContext appContext: Context): BrowserRemoteRepository {
        return BrowserRemoteRepository() //impl
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): BrowserLocalRepository {
        return BrowserLocalRepository() // impl
    }

    // @ApplicationContext in den Parameter, ist default binding
    @Provides
    fun providesBrowserRepository(
        remoteRepository: BrowserRemoteRepository,
        localRepository: BrowserLocalRepository
    ): BrowserRepository {
        return BrowserRepositoryImpl(remoteRepository, localRepository)
    }

}