package five_skills.skill_detail.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.all_in_one.databinding.ActivityFiveSkillsDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SkillDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFiveSkillsDetailBinding

    @Inject
    lateinit var viewModel: SkillDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFiveSkillsDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}