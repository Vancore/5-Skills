package five_skills.skill_browser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.all_in_one.databinding.ActivityBrowserBinding
import dagger.hilt.android.AndroidEntryPoint
import shared.models.SkillItem
import javax.inject.Inject

@AndroidEntryPoint
class BrowserActivity : AppCompatActivity(), BrowserItemClickListener {

    private lateinit var binding: ActivityBrowserBinding

    @Inject
    lateinit var browserViewModel: BrowserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBrowserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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

}