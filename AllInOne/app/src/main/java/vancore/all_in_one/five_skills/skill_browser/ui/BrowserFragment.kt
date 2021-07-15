package vancore.all_in_one.five_skills.skill_browser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.all_in_one.databinding.FragmentBrowserBinding
import dagger.hilt.android.AndroidEntryPoint
import vancore.all_in_one.five_skills.skill_browser.data.models.BrowserItem
import javax.inject.Inject

@AndroidEntryPoint
class BrowserFragment : Fragment(), BrowserItemClickListener {

    private var _binding: FragmentBrowserBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var browserViewModel: BrowserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        browserViewModel.doSomething()

        binding.rvBrowser.adapter = BrowserAdapter(listOf(
            BrowserItem("Skill Name 1", "Skill Title 1", "Skill Description 1", 1),
            BrowserItem("Skill Name 2", "Skill Title 2", "Skill Description 1", 1),
            BrowserItem("Skill Name 3", "Skill Title 3", "Skill Description 1", 1),
            BrowserItem("Skill Name 4", "Skill Title 4", "Skill Description 1", 1),
            BrowserItem("Skill Name 5", "Skill Title 5", "Skill Description 1", 1),
            BrowserItem("Skill Name 6", "Skill Title 6", "Skill Description 1", 1),
            BrowserItem("Skill Name 7", "Skill Title 7", "Skill Description 1", 1),
            BrowserItem("Skill Name 8", "Skill Title 8", "Skill Description 1", 1),
            BrowserItem("Skill Name 9", "Skill Title 9", "Skill Description 1", 1),
            BrowserItem("Skill Name 10", "Skill Title 10", "Skill Description 1", 1),
            BrowserItem("Skill Name 11", "Skill Title 11", "Skill Description 1", 1),
            BrowserItem("Skill Name 12", "Skill Title 12", "Skill Description 1", 1),
            BrowserItem("Skill Name 13", "Skill Title 13", "Skill Description 1", 1),
            BrowserItem("Skill Name 14", "Skill Title 14", "Skill Description 1", 1),
            BrowserItem("Skill Name 15", "Skill Title 15", "Skill Description 1", 1),
            BrowserItem("Skill Name 16", "Skill Title 16", "Skill Description 1", 1),
            BrowserItem("Skill Name 17", "Skill Title 17", "Skill Description 1", 1),
        ), this)

    }

    override fun onBrowserItemClicked(item: BrowserItem) {
        // do some stuff
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}