package vancore.five_skills.data

import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.data.models.SubcategoryItem

interface FiveSkillsRepository {
    suspend fun loadCategories(): List<CategoryItem>
    suspend fun loadSubcategories(categoryId: String): List<SubcategoryItem>
    suspend fun fetchSkillListFromUser(firebaseUserId: String): ArrayList<SkillItem>
    suspend fun addSkillForUser(firebaseUserId: String, skillItem: SkillItem)
    fun addFirebaseUser(firebaseUserId: String, email: String)
}