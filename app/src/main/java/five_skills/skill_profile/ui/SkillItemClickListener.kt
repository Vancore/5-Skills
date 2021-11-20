package five_skills.skill_profile.ui

import shared.models.SkillItem

interface SkillItemClickListener {
    fun onSkillItemClicked(item: SkillItem)
}