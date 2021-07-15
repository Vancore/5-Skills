package vancore.all_in_one.five_skills.skill_browser.ui

import vancore.all_in_one.five_skills.skill_browser.data.models.BrowserItem

interface BrowserItemClickListener {
    fun onBrowserItemClicked(item: BrowserItem)
}