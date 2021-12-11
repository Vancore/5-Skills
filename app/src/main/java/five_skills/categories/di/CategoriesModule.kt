package five_skills.categories.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import five_skills.categories.data.CategoriesLocalRepository
import five_skills.categories.data.CategoriesRemoteRepository
import five_skills.categories.data.CategoriesRepository
import five_skills.categories.data.CategoriesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoriesModule {

    @Provides
    @Singleton
    fun provideRemoteDatabase(@ApplicationContext appContext: Context): CategoriesRemoteRepository {
        return CategoriesRemoteRepository() //impl
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): CategoriesLocalRepository {
        return CategoriesLocalRepository() // impl
    }

    // @ApplicationContext in den Parameter, ist default binding
    @Provides
    fun providesBrowserRepository(
        remoteRepository: CategoriesRemoteRepository,
        localRepository: CategoriesLocalRepository
    ): CategoriesRepository {
        return CategoriesRepositoryImpl(remoteRepository, localRepository)
    }
}