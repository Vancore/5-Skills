package vancore.five_skills.data

import vancore.five_skills.components.CategoryItem
import vancore.five_skills.components.SubCategoryItem

interface CategoryListRepository {
    suspend fun loadCategories(): List<CategoryItem>
    suspend fun loadSubCategories(categoryId: String, category: String): List<SubCategoryItem>
}