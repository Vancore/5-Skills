package vancore.all_in_one.five_skills.skill_browser.ui

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BrowserFragment : Fragment() {


//    GrantType: "client_credentials"
//    Client_Secret: "LEWHj96ZC2dVT94m8jMSDHKAvrYRMSYK"
//    Client_ID"83d65959a7484ec18f48a60f2f9f4c00"

    @Inject
    lateinit var browserViewModel: BrowserViewModel

    override fun onStart() {
        super.onStart()

        browserViewModel.doSomething()
    }


}