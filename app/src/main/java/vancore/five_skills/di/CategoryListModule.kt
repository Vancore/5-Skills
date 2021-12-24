package vancore.five_skills.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vancore.five_skills.usecases.AuthenticationUseCase
import vancore.five_skills.data.FiveSkillsRepository
import vancore.five_skills.data.FiveSkillsRepositoryImpl
import vancore.five_skills.data.FiveSkillsLocalRepository
import vancore.five_skills.data.FiveSkillsRemoteRepository
import vancore.five_skills.usecases.ProfileSkillListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryListModule {

    @Provides
    @Singleton
    fun provideRemoteDatabase(@ApplicationContext appContext: Context): FiveSkillsRemoteRepository {
        return FiveSkillsRemoteRepository() //impl
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): FiveSkillsLocalRepository {
        return FiveSkillsLocalRepository() // impl
    }

    // @ApplicationContext in den Parameter, ist default binding
    @Provides
    fun providesFiveSkillsRepository(
        remoteRepository: FiveSkillsRemoteRepository,
        localRepository: FiveSkillsLocalRepository
    ): FiveSkillsRepository {
        return FiveSkillsRepositoryImpl(remoteRepository, localRepository)
    }

    @Provides
    fun providesAuthenticationUseCase(): AuthenticationUseCase {
        return AuthenticationUseCase()
    }

    @Provides
    fun providesProfileSkillListUseCase(
        repository: FiveSkillsRepository
    ): ProfileSkillListUseCase {
        return ProfileSkillListUseCase(repository = repository)
    }
}