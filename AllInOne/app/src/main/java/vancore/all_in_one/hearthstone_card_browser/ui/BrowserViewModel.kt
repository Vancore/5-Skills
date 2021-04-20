package vancore.all_in_one.hearthstone_card_browser.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import vancore.all_in_one.hearthstone_card_browser.data.BrowserRepository
import javax.inject.Inject

@HiltViewModel
class BrowserViewModel @Inject constructor(
    private val browserRepository: BrowserRepository
) : ViewModel() {

    fun doSomething() {

    }
}