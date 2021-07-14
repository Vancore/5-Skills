package vancore.all_in_one.five_skills.skill_browser.ui

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BrowserFragment : Fragment() {

    @Inject
    lateinit var browserViewModel: BrowserViewModel

    override fun onStart() {
        super.onStart()

        browserViewModel.doSomething()
    }


}