package five_skills.categories.data

import five_skills.shared.models.CategoryItem

interface CategoriesRepository {
    suspend fun loadCategories(): List<CategoryItem>
}