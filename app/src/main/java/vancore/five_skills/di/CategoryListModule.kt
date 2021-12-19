package vancore.five_skills.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vancore.five_skills.data.CategoryListRepository
import vancore.five_skills.data.CategoryListRepositoryImpl
import vancore.five_skills.data.CategoryListLocalRepository
import vancore.five_skills.data.CategoryListRemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryListModule {

    @Provides
    @Singleton
    fun provideRemoteDatabase(@ApplicationContext appContext: Context): CategoryListRemoteRepository {
        return CategoryListRemoteRepository() //impl
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): CategoryListLocalRepository {
        return CategoryListLocalRepository() // impl
    }

    // @ApplicationContext in den Parameter, ist default binding
    @Provides
    fun providesCategoryListRepository(
        listRemoteRepository: CategoryListRemoteRepository,
        listLocalRepository: CategoryListLocalRepository
    ): CategoryListRepository {
        return CategoryListRepositoryImpl(listRemoteRepository, listLocalRepository)
    }
}