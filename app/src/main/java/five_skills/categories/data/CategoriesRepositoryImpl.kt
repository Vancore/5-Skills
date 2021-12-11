package five_skills.categories.data

import five_skills.shared.models.CategoryItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepositoryImpl @Inject constructor(
  private val remoteRepository: CategoriesRemoteRepository,
  private val localRepository: CategoriesLocalRepository
) : CategoriesRepository {

  override suspend fun loadCategories(): List<CategoryItem> {
    return remoteRepository.loadCategories()
  }
}