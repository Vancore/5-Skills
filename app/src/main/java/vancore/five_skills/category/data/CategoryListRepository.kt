package vancore.five_skills.category.data

import vancore.five_skills.shared.CategoryItem

interface CategoryListRepository {
    suspend fun loadCategories(): List<CategoryItem>
}