package vancore.all_in_one.five_skills

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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

        // Set first fragment here
        activeFragment = browserFragment
        displayFragment(activeFragment, getString(R.string.menu_five_skills_browser))
        fragmentManager.primaryNavigationFragment

        binding.bnvFiveSkills.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.skillProfileFragment -> {
                    Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                    displayFragment(profileFragment, getString(R.string.menu_five_skills_profile))
                    true
                }
                R.id.browserFragment -> {
                    Toast.makeText(this, "Browser clicked", Toast.LENGTH_SHORT).show()
                    displayFragment(browserFragment, getString(R.string.menu_five_skills_browser))
                    true
                }
                else -> false
            }
        }
    }

    private fun displayFragment(fragment: Fragment, navigationBarTitle: String) { //switching fragment
        changeFragment(fragment, fragment::class.java.simpleName, navigationBarTitle)
    }

    private fun changeFragment(fragment: Fragment, tagFragmentName: String?, navigationBarTitle: String) {
        activeFragment = fragment
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction().apply {
            val currentFragment: Fragment? = fm.primaryNavigationFragment
            if (currentFragment != null) {
                detach(currentFragment)
            }
            var fragmentTemp: Fragment? = fm.findFragmentByTag(tagFragmentName)
            if (fragmentTemp == null) {
                fragmentTemp = fragment
                add(R.id.container, fragmentTemp, tagFragmentName)
            } else {
                attach(fragmentTemp)
            }
            setPrimaryNavigationFragment(fragmentTemp)
            setReorderingAllowed(true)
            commitNowAllowingStateLoss()
        }
        this.title = navigationBarTitle
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}