package vancore.all_in_one.five_skills.skill_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.all_in_one.databinding.FragmentSkillProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import vancore.all_in_one.five_skills.FiveSkillsActivity
import javax.inject.Inject

@AndroidEntryPoint
class SkillProfileFragment : Fragment() {

    private var _binding: FragmentSkillProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var profileViewModel: SkillProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkillProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        profileViewModel.doSomething()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}