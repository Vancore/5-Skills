package vancore.all_in_one.five_skills.skill_profile.data

import com.google.firebase.auth.FirebaseUser
import vancore.all_in_one.shared.models.SkillItem

interface SkillProfileRepository {
    fun getSkillsForProfile(user: FirebaseUser?): List<SkillItem>
    fun saveSkill(skill: SkillItem)
    fun saveSkills(user: FirebaseUser?, list: List<SkillItem>)
}