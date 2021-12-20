package vancore.five_skills.data

import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SubcategoryItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FiveSkillsRepositoryImpl @Inject constructor(
    private val listRemoteRepository: FiveSkillsRemoteRepository,
    private val listLocalRepository: FiveSkillsLocalRepository
) : FiveSkillsRepository {

    override suspend fun loadCategories(): List<CategoryItem> {
        return listRemoteRepository.loadCategories()
    }

    override suspend fun loadSubcategories(categoryId: String): List<SubcategoryItem> {
        return listRemoteRepository.loadSubCategories(categoryId = categoryId)
    }
}