package vancore.five_skills.data

import vancore.five_skills.components.CategoryItem
import vancore.five_skills.components.SubCategoryItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryListRepositoryImpl @Inject constructor(
    private val listRemoteRepository: CategoryListRemoteRepository,
    private val listLocalRepository: CategoryListLocalRepository
) : CategoryListRepository {

    override suspend fun loadCategories(): List<CategoryItem> {
        return listRemoteRepository.loadCategories()
    }

    override suspend fun loadSubCategories(categoryId: String, category: String): List<SubCategoryItem> {
        return listRemoteRepository.loadSubCategories(categoryId = categoryId, category = category)
    }
}