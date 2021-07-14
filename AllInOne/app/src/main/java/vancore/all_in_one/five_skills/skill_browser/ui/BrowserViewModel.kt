package vancore.all_in_one.five_skills.skill_browser.ui

import androidx.lifecycle.ViewModel
import vancore.all_in_one.five_skills.skill_browser.data.BrowserRepository
import javax.inject.Inject

class BrowserViewModel @Inject constructor(
    private val browserRepository: BrowserRepository
) : ViewModel() {

    fun doSomething() {
        browserRepository.browserMethod()
    }
}