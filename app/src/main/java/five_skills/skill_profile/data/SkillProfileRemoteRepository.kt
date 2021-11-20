package five_skills.skill_profile.data

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import five_skills.skill_profile.extensions.getSkillListFromUser
import shared.models.SkillItem

class SkillProfileRemoteRepository {
    private val db = Firebase.firestore

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