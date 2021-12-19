package vancore.five_skills.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import vancore.five_skills.components.CategoryItem
import kotlinx.coroutines.tasks.await
import vancore.five_skills.getCategoryItems
import vancore.five_skills.getSubCategoryItems
import vancore.five_skills.components.SubCategoryItem

class CategoryListRemoteRepository {
    private val db = Firebase.firestore

    suspend fun loadCategories(): List<CategoryItem> {
        return db.collection(CATEGORIES)
            .get()
            .await()
            .getCategoryItems()
    }

    suspend fun loadSubCategories(category: String, categoryId: String): List<SubCategoryItem> {
        return db.collection("$CATEGORIES/6djkARuqgOvSotJWdoXO/$SUBCATEGORIES")
            .get()
            .await()
            .getSubCategoryItems()
    }

    companion object {
        const val CATEGORIES = "Categories"
        const val SUBCATEGORIES = "Subcategories"
    }
}