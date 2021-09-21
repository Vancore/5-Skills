package vancore.all_in_one.five_skills.skill_profile.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import vancore.all_in_one.shared.models.SkillItem

class SkillProfileRemoteRepository {
    private val db = Firebase.firestore

    fun getSkillsForProfile(): List<SkillItem> {
        db.firestoreSettings

        return listOf(
            SkillItem("displayName1", "SkillTitle1", "SkillDescription1", 1),
            SkillItem("displayName2", "SkillTitle2", "SkillDescription2", 2),
            SkillItem("displayName3", "SkillTitle3", "SkillDescription3", 3),
            SkillItem("displayName4", "SkillTitle4", "SkillDescription4", 4),
            SkillItem("displayName5", "SkillTitle5", "SkillDescription5", 5)
        )
    }
}