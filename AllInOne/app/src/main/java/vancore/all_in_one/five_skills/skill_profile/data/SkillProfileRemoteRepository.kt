package vancore.all_in_one.five_skills.skill_profile.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import vancore.all_in_one.five_skills.skill_profile.extensions.getSkillListFromUser
import vancore.all_in_one.five_skills.skill_profile.extensions.toSkillItem
import vancore.all_in_one.five_skills.skill_profile.extensions.toSkillItemList
import vancore.all_in_one.shared.models.SkillItem

class SkillProfileRemoteRepository {
    private val db = Firebase.firestore

    private val _listOfSkills: MutableLiveData<List<SkillItem>> = MutableLiveData()
    val listOfSkills: LiveData<List<SkillItem>>
        get() = _listOfSkills

    suspend fun getSkillsOfUser(user: FirebaseUser?): List<SkillItem> {
        return db.collection(USER_LIST)
            .document("${user?.uid}")
            .get()
            .await()
            .getSkillListFromUser()
    }

    // Merge both methods
    fun saveSkills(user: FirebaseUser?, skills: List<SkillItem>) {
        //Maybe enrich the FirebaseUser for more data
        val skillsDB = db.collection(USER_LIST)

        val data1 = hashMapOf(
            "name" to user?.displayName,
            "e-mail" to user?.email,
            FIVE_SKILLS to skills
        )
        skillsDB.document("${user?.uid}").set(data1)
    }

    fun saveSkill(skill: SkillItem) {

    }

    companion object {
        const val USER_LIST = "User List"
        const val FIVE_SKILLS = "5 Skills"
    }
}