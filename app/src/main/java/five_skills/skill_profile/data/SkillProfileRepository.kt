package five_skills.skill_profile.data

import com.google.firebase.auth.FirebaseUser
import shared.models.SkillItem

interface SkillProfileRepository {
    suspend fun getSkillsForProfile(user: FirebaseUser?): List<SkillItem>
    fun saveSkill(skill: SkillItem)
    fun saveSkills(user: FirebaseUser?, list: List<SkillItem>)
}