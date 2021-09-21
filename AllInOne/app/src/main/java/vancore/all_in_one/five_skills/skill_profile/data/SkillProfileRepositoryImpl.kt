package vancore.all_in_one.five_skills.skill_profile.data

import vancore.all_in_one.shared.models.SkillItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkillProfileRepositoryImpl @Inject constructor(
    private val remoteRepository: SkillProfileRemoteRepository,
    private val localRepository: SkillProfileLocalRepository
) : SkillProfileRepository {

    override fun getSkillsForProfile(): List<SkillItem> {
        return remoteRepository.getSkillsForProfile()
    }

}