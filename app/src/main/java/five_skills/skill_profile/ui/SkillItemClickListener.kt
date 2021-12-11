package five_skills.skill_profile.ui

import five_skills.shared.models.SkillItem


interface SkillItemClickListener {
    fun onSkillItemClicked(item: SkillItem)
}