package vancore.five_skills.category.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vancore.five_skills.category.data.CategoryListRepository
import vancore.five_skills.category.data.CategoryListRepositoryImpl
import vancore.five_skills.category.data.CategoryLocalRepository
import vancore.five_skills.category.data.CategoryRemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoriesModule {

    @Provides
    @Singleton
    fun provideRemoteDatabase(@ApplicationContext appContext: Context): CategoryRemoteRepository {
        return CategoryRemoteRepository() //impl
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): CategoryLocalRepository {
        return CategoryLocalRepository() // impl
    }

    // @ApplicationContext in den Parameter, ist default binding
    @Provides
    fun providesBrowserRepository(
        remoteRepository: CategoryRemoteRepository,
        localRepository: CategoryLocalRepository
    ): CategoryListRepository {
        return CategoryListRepositoryImpl(remoteRepository, localRepository)
    }
}