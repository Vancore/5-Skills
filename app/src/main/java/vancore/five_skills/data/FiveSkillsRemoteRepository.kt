package vancore.five_skills.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.data.models.SubcategoryItem
import vancore.five_skills.getCategoryItems
import vancore.five_skills.getSkillListFromUser
import vancore.five_skills.getSubCategoryItems
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
            .getSkillListFromUser()
    }

    fun addSkillForUser(firebaseUserId: String, skillItem: SkillItem) {
        val skillItemData = hashMapOf(
            "userId" to skillItem.userId,
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

    fun fetchSkillsForSubcategory(subcategoryId: String): ArrayList<SkillItem> {
        TODO("Not yet implemented")
        db.collection(USER_LIST)
        //for(user in db.collection(USER_LIST))
    }

    companion object {
        const val CATEGORIES = "Categories"
        const val SUBCATEGORIES = "Subcategories"
        const val USER_LIST = "User List"
        const val FIVE_SKILLS = "5 Skills"
    }
}