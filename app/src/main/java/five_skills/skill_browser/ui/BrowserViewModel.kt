package five_skills.skill_browser.ui

import androidx.lifecycle.ViewModel
import five_skills.skill_browser.data.BrowserRepository
import javax.inject.Inject

class BrowserViewModel @Inject constructor(
    private val browserRepository: BrowserRepository
) : ViewModel() {

    fun doSomething() {
        browserRepository.browserMethod()
    }
}