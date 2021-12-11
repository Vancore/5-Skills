package five_skills.browse_categories.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import five_skills.browse_categories.data.BrowserLocalRepository
import five_skills.browse_categories.data.BrowserRemoteRepository
import five_skills.browse_categories.data.BrowseCategoriesRepository
import five_skills.browse_categories.data.BrowseCategoriesRepositoryImpl
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
    ): BrowseCategoriesRepository {
        return BrowseCategoriesRepositoryImpl(remoteRepository, localRepository)
    }
}