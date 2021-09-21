package vancore.all_in_one.five_skills.skill_browser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.all_in_one.databinding.FragmentBrowserBinding
import dagger.hilt.android.AndroidEntryPoint
import vancore.all_in_one.shared.models.SkillItem
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        browserViewModel.doSomething()

        binding.rvBrowser.adapter = BrowserAdapter(listOf(
            SkillItem("Skill Title 1", "Skill Description 1", 1),
            SkillItem("Skill Title 2", "Skill Description 1", 1),
            SkillItem("Skill Title 3", "Skill Description 1", 1),
            SkillItem("Skill Title 4", "Skill Description 1", 1),
            SkillItem("Skill Title 5", "Skill Description 1", 1),
            SkillItem("Skill Title 6", "Skill Description 1", 1),
            SkillItem("Skill Title 7", "Skill Description 1", 1),
            SkillItem("Skill Title 8", "Skill Description 1", 1),
            SkillItem("Skill Title 9", "Skill Description 1", 1),
            SkillItem("Skill Title 10", "Skill Description 1", 1),
            SkillItem("Skill Title 11", "Skill Description 1", 1),
            SkillItem("Skill Title 12", "Skill Description 1", 1),
            SkillItem("Skill Title 13", "Skill Description 1", 1),
            SkillItem("Skill Title 14", "Skill Description 1", 1),
            SkillItem("Skill Title 15", "Skill Description 1", 1),
            SkillItem("Skill Title 16", "Skill Description 1", 1),
            SkillItem("Skill Title 17", "Skill Description 1", 1),
        ), this)

    }

    override fun onBrowserItemClicked(item: SkillItem) {
        // do some stuff
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}