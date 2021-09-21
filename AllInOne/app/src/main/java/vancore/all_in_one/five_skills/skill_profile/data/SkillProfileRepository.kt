package vancore.all_in_one.five_skills.skill_profile.data

import vancore.all_in_one.shared.models.SkillItem

interface SkillProfileRepository {
    fun getSkillsForProfile(): List<SkillItem>
}