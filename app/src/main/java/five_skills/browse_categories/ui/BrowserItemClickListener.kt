package five_skills.browse_categories.ui

import shared.models.SkillItem

interface BrowserItemClickListener {
    fun onBrowserItemClicked(item: SkillItem)
}