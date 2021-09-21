package vancore.all_in_one.five_skills.skill_profile.data

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import vancore.all_in_one.shared.models.SkillItem

class SkillProfileRemoteRepository {
    private val db = Firebase.firestore

    fun getSkillsForProfile(user: FirebaseUser?): List<SkillItem> {
        var listOfSkills = mutableListOf<SkillItem>()

        val docRef = db.collection(USER_LIST)
            .document("ID:${user?.uid}, Name:${user?.displayName}")

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val fiveSkillsList = (document.data?.get(FIVE_SKILLS) as Iterable<*>).toList()
                    for (skill in fiveSkillsList) {
                        listOfSkills.add(
                            SkillItem(
                                skillTitle = (skill as HashMap<*, *>)["skillTitle"].toString(),
                                skillDescription = skill["skillDescription"].toString(),
                                skillLevel = skill["skillLevel"].toString().toInt()
                            )
                        )
                    }
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }


        return listOf(
            SkillItem("SkillTitle1", "SkillDescription1", 1),
            SkillItem("SkillTitle2", "SkillDescription2", 2),
            SkillItem("SkillTitle3", "SkillDescription3", 3),
            SkillItem("SkillTitle4", "SkillDescription4", 4),
            SkillItem("SkillTitle5", "SkillDescription5", 5)
        )
    }

    // Merge both methods
    fun saveSkills(user: FirebaseUser?, skills: List<SkillItem>) {
        //Maybe enrich the FirebaseUser for more data
        val skillsDB = db.collection(USER_LIST)

        val data1 = hashMapOf(
            "name" to user?.displayName,
            "e-mail" to user?.email,
            "5 Skills" to skills
        )
        skillsDB.document("ID:${user?.uid}, Name:${user?.displayName}").set(data1)
    }

    fun saveSkill(skill: SkillItem) {

    }

    companion object {
        const val USER_LIST = "User List"
        const val FIVE_SKILLS = "5 Skills"
    }
}