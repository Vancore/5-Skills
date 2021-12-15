package vancore.five_skills.category.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import vancore.five_skills.shared.CategoryItem
import kotlinx.coroutines.tasks.await
import vancore.five_skills.category.extensions.getCategoryItems

class CategoryRemoteRepository {
    private val db = Firebase.firestore

    suspend fun loadCategories(): List<CategoryItem> {
        return db.collection(CATEGORIES)
            .get()
            .await()
            .getCategoryItems()
    }

    companion object {
        const val CATEGORIES = "Categories"
    }
}