package vancore.five_skills.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SubcategoryItem
import vancore.five_skills.getCategoryItems
import vancore.five_skills.getSubCategoryItems

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

    companion object {
        const val CATEGORIES = "Categories"
        const val SUBCATEGORIES = "Subcategories"
    }
}