package vancore.all_in_one.five_skills.skill_profile.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vancore.all_in_one.five_skills.skill_profile.data.SkillProfileLocalRepository
import vancore.all_in_one.five_skills.skill_profile.data.SkillProfileRemoteRepository
import vancore.all_in_one.five_skills.skill_profile.data.SkillProfileRepository
import vancore.all_in_one.five_skills.skill_profile.data.SkillProfileRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SkillProfileModule {

    @Provides
    @Singleton
    fun provideRemoteDatabase(@ApplicationContext appContext: Context): SkillProfileRemoteRepository {
        return SkillProfileRemoteRepository() //impl
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): SkillProfileLocalRepository {
        return SkillProfileLocalRepository() // impl
    }

    // @ApplicationContext in den Parameter, ist default binding
    @Provides
    fun providesBrowserRepository(
        remoteRepository: SkillProfileRemoteRepository,
        localRepository: SkillProfileLocalRepository
    ): SkillProfileRepository {
        return SkillProfileRepositoryImpl(remoteRepository, localRepository)
    }

}