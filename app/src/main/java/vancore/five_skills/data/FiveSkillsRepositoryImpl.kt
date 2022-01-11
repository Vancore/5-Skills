package vancore.five_skills.data

import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.data.models.SubcategoryItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FiveSkillsRepositoryImpl @Inject constructor(
    private val remoteRepository: FiveSkillsRemoteRepository,
    private val localRepository: FiveSkillsLocalRepository
) : FiveSkillsRepository {

    override suspend fun loadCategories(): List<CategoryItem> {
        return remoteRepository.loadCategories()
    }

    override suspend fun loadSubcategories(categoryId: String): List<SubcategoryItem> {
        return remoteRepository.loadSubCategories(categoryId = categoryId)
    }

    override suspend fun fetchSkillListFromUser(firebaseUserId: String): ArrayList<SkillItem> {
        return remoteRepository.fetchUserSkillList(firebaseUserId = firebaseUserId)
    }

    override suspend fun addSkillForUser(firebaseUserId: String, skillItem: SkillItem) {
        remoteRepository.addSkillForUser(firebaseUserId, skillItem)
    }

    override fun addFirebaseUser(firebaseUserId: String, email: String) {
        remoteRepository.addFirebaseUser(firebaseUserId, email = email)
    }

    override suspend fun fetchSkillsForSubcategory(subcategoryId: String): ArrayList<SkillItem> {
        return remoteRepository.fetchSkillsForSubcategory(subcategoryId = subcategoryId)
    }
}