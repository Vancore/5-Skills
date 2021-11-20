package five_skills.skill_browser.ui

import shared.models.SkillItem

interface BrowserItemClickListener {
    fun onBrowserItemClicked(item: SkillItem)
}