package vancore.all_in_one.five_skills.skill_profile.ui

import androidx.lifecycle.ViewModel
import vancore.all_in_one.five_skills.skill_profile.data.SkillProfileRepository
import javax.inject.Inject

class SkillProfileViewModel @Inject constructor(
    private val profileRepository: SkillProfileRepository
) : ViewModel() {

    fun doSomething() {
        profileRepository.profileMethod()
    }
}