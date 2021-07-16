package vancore.all_in_one.five_skills

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.all_in_one.R
import com.example.all_in_one.databinding.ActivityFiveSkillsBinding
import dagger.hilt.android.AndroidEntryPoint
import vancore.all_in_one.five_skills.skill_browser.ui.BrowserFragment
import vancore.all_in_one.five_skills.skill_profile.ui.SkillProfileFragment


@AndroidEntryPoint
class FiveSkillsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFiveSkillsBinding

    private val profileFragment = SkillProfileFragment()
    private val browserFragment = BrowserFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = profileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiveSkillsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fragmentManager.beginTransaction().apply {
            add(R.id.container, browserFragment, "BrowserFragment").show(browserFragment)
            add(R.id.container, profileFragment, "SkillProfileFragment").hide(profileFragment)
        }.commit()

        // First fragment here
        activeFragment = browserFragment
        this.title = "Browser"

        binding.bnvFiveSkills.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.skillProfileFragment -> {
                    Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                    fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                    activeFragment = profileFragment
                    this.title = "Profile"
                    true
                }
                R.id.browserFragment -> {
                    Toast.makeText(this, "Browser clicked", Toast.LENGTH_SHORT).show()
                    fragmentManager.beginTransaction().hide(activeFragment).show(browserFragment).commit()
                    activeFragment = browserFragment
                    this.title = "Browser"
                    true
                }
                else -> false
            }
        }
    }
}