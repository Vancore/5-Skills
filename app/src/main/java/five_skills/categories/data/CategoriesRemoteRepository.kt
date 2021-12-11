package five_skills.categories.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import five_skills.categories.extensions.getCategoryItems
import five_skills.shared.models.CategoryItem
import kotlinx.coroutines.tasks.await

class CategoriesRemoteRepository {

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