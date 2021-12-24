package vancore.five_skills.data

import com.google.firebase.auth.FirebaseUser
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SkillItem
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

    override suspend fun fetchSkillListFromUser(firebaseUserId: String): ArrayList<SkillItem> {
        return listRemoteRepository.fetchUserSkillList(firebaseUserId = firebaseUserId)
    }
}