package five_skills.browse_categories.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrowseCategoriesRepositoryImpl @Inject constructor(
    private val remoteRepository: BrowserRemoteRepository,
    private val localRepository: BrowserLocalRepository
) : BrowseCategoriesRepository {
    override fun loadCategories() {
    }
}