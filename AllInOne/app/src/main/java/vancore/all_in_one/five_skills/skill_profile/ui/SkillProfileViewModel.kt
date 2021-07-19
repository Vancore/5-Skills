package vancore.all_in_one.five_skills.skill_profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import vancore.all_in_one.five_skills.skill_profile.data.SkillProfileRepository
import javax.inject.Inject

class SkillProfileViewModel @Inject constructor(
    private val profileRepository: SkillProfileRepository
) : ViewModel() {

    private val _isUserOnline: MutableLiveData<Boolean> = MutableLiveData()
    val isUserOnline: LiveData<Boolean>
        get() = _isUserOnline

    fun doSomething() {
        profileRepository.profileMethod()
    }

    fun checkIfUserIsOnline(auth: FirebaseAuth) {
        profileRepository.profileMethod()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _isUserOnline.postValue(true)
        } else {
            _isUserOnline.postValue(false)
        }
    }
}