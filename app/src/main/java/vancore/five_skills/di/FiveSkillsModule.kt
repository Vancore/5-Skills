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
import vancore.five_skills.usecases.AddSkillUseCase
import vancore.five_skills.usecases.ProfileSkillListUseCase
import vancore.five_skills.usecases.SearchSkillsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FiveSkillsModule {

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

    // @ApplicationContext can be put as parameter, as it is default binding
    @Provides
    fun providesFiveSkillsRepository(
        remoteRepository: FiveSkillsRemoteRepository,
        localRepository: FiveSkillsLocalRepository
    ): FiveSkillsRepository {
        return FiveSkillsRepositoryImpl(remoteRepository, localRepository)
    }

    @Provides
    fun providesAuthenticationUseCase(
        repository: FiveSkillsRepository
    ): AuthenticationUseCase {
        return AuthenticationUseCase(repository = repository)
    }

    @Provides
    fun providesProfileSkillListUseCase(
        repository: FiveSkillsRepository
    ): ProfileSkillListUseCase {
        return ProfileSkillListUseCase(repository = repository)
    }

    @Provides
    fun providesAddSkillListUseCase(
        repository: FiveSkillsRepository
    ): AddSkillUseCase {
        return AddSkillUseCase(repository = repository)
    }

    @Provides
    fun providesSearchSkillsUseCase(
        repository: FiveSkillsRepository
    ): SearchSkillsUseCase {
        return SearchSkillsUseCase(repository = repository)
    }
}