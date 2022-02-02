package vancore.five_skills.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.data.models.SubcategoryItem
import vancore.five_skills.getCategoryItems
import vancore.five_skills.getSkills
import vancore.five_skills.getSubCategoryItems
import vancore.five_skills.getUserList
import java.util.ArrayList

class FiveSkillsRemoteRepository {
    private val db = Firebase.firestore

    suspend fun loadCategories(): List<CategoryItem> {
        return db.collection(CATEGORIES)
            .get()
            .await()
            .getCategoryItems()
    }

    suspend fun loadSubCategories(categoryId: String): List<SubcategoryItem> {
        return db.collection("$CATEGORIES/$categoryId/$SUBCATEGORIES")
            .get()
            .await()
            .getSubCategoryItems()
    }

    suspend fun fetchUserSkillList(firebaseUserId: String): ArrayList<SkillItem> {
        return db.collection(USER_LIST)
            .document(firebaseUserId)
            .collection(FIVE_SKILLS)
            .get()
            .await()
            .getSkills()
    }

    fun addSkillForUser(firebaseUserId: String, skillItem: SkillItem) {
        val skillItemData = hashMapOf(
            USER_ID to skillItem.userId,
            SUBCATEGORY_ID to skillItem.subcategoryId,
            CATEGORY_ID to skillItem.categoryId,
            "title" to skillItem.title,
            "description" to skillItem.description,
            "selfRating" to skillItem.selfRating,
            "ranking" to skillItem.ranking, // ToDo: Finish the flow with adding images and category/subcategory
        )
        db.collection(USER_LIST)
            .document(firebaseUserId)
            .collection(FIVE_SKILLS)
            .add(skillItemData)
    }

    fun addFirebaseUser(firebaseUserId: String, email: String) {
        val emailData = hashMapOf("email" to email)
        db.collection(USER_LIST)
            .document(firebaseUserId)
            .set(emailData)
    }

    suspend fun fetchSkillsForSubcategory(subcategoryId: String): ArrayList<SkillItem> {
        val skillList = arrayListOf<SkillItem>()
        val userList = db.collection(USER_LIST)
            .get()
            .await()
            .getUserList()

        userList.forEach { user ->
            skillList.addAll(
                db.collection("$USER_LIST/${user.firebaseId}/$FIVE_SKILLS")
                    .whereEqualTo(SUBCATEGORY_ID, subcategoryId)
                    .get()
                    .await()
                    .getSkills()
            )
        }
        return skillList
    }

    suspend fun deleteSkill(skillItem: SkillItem): Boolean {
        var success = false
        db.collection("$USER_LIST/${skillItem.userId}/$FIVE_SKILLS")
            .document(skillItem.skillId)
            .delete()
            .addOnSuccessListener { success = true }
            .await()
        return success
    }

    companion object {
        const val CATEGORIES = "Categories"
        const val CATEGORY_ID = "CategoryId"
        const val SUBCATEGORIES = "Subcategories"
        const val SUBCATEGORY = "Subcategory"
        const val SUBCATEGORY_ID = "SubcategoryId"
        const val USER_ID = "userId"
        const val USER_LIST = "User List"
        const val FIVE_SKILLS = "5 Skills"
    }
}