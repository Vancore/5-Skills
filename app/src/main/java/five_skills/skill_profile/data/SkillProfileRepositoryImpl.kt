package five_skills.skill_profile.data

import com.google.firebase.auth.FirebaseUser
import five_skills.shared.models.SkillItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkillProfileRepositoryImpl @Inject constructor(
    private val remoteRepository: SkillProfileRemoteRepository,
    private val localRepository: SkillProfileLocalRepository
) : SkillProfileRepository {

    override suspend fun getSkillsForProfile(user: FirebaseUser?): List<SkillItem> {
        return remoteRepository.getSkillsOfUser(user)
    }

    override fun saveSkill(skill: SkillItem) {
        remoteRepository.saveSkill(skill)
    }

    override fun saveSkills(user: FirebaseUser?, list: List<SkillItem>) {
        remoteRepository.saveSkills(user, list)
    }
}