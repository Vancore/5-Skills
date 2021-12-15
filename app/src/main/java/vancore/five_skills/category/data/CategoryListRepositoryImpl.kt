package vancore.five_skills.category.data

import vancore.five_skills.shared.CategoryItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryListRepositoryImpl @Inject constructor(
    private val remoteRepository: CategoryRemoteRepository,
    private val localRepository: CategoryLocalRepository
) : CategoryListRepository {

    override suspend fun loadCategories(): List<CategoryItem> {
        return remoteRepository.loadCategories()
    }
}