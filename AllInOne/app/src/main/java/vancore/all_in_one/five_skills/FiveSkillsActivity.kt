package vancore.all_in_one.five_skills

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.all_in_one.R
import com.example.all_in_one.databinding.ActivityFiveSkillsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FiveSkillsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFiveSkillsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiveSkillsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        binding.bnvFiveSkills.setOnNavigationItemSelectedListener { item ->
//            when(item.itemId) {
//                R.id.skillProfile ->
//            }
//        }

        val navigationController = findNavController(R.id.host_fragment_five_skills)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.browserFragment, R.id.skillProfile))
        setupActionBarWithNavController(navigationController, appBarConfiguration)
        binding.bnvFiveSkills.setupWithNavController(navigationController)

    }

}