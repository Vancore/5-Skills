package vancore.five_skills

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vancore.five_skills.usecases.AuthenticationUseCase
import vancore.five_skills.data.FiveSkillsRepository
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SubcategoryItem
import vancore.five_skills.usecases.AddSkillUseCase
import vancore.five_skills.usecases.ProfileSkillListUseCase
import javax.inject.Inject

class FiveSkillsViewModel @Inject constructor(
    private val categoriesRepository: FiveSkillsRepository,
    private val authenticationUseCase: AuthenticationUseCase,
    private val profileSkillListUseCase: ProfileSkillListUseCase,
    private val addSkillUseCase: AddSkillUseCase
) : ViewModel() {

    var categoriesList = mutableStateListOf<CategoryItem>()
        private set

    var subcategoriesList = mutableStateListOf<SubcategoryItem>()
        private set

    var authenticationState = authenticationUseCase.authenticationState
    var profileSkillListState = profileSkillListUseCase.profileSkillListState
    var addSkillState = addSkillUseCase.addSkillState

    init {
        viewModelScope.launch {
            categoriesList.addAll(categoriesRepository.loadCategories())
        }
    }

    fun fetchSubcategoriesFor(categoryID: String) {
        viewModelScope.launch {
            subcategoriesList.clear()
            subcategoriesList.addAll(categoriesRepository.loadSubcategories(categoryId = categoryID))
        }
    }

    fun fetchSkillsForUser(firebaseUserId: String) {
        viewModelScope.launch {
            profileSkillListUseCase.fetchSkillListForUser(firebaseUserId)
        }
    }

    fun loginClicked(user: String, password: String) {
        authenticationUseCase.login(email = user, password = password)
    }

    fun registerClicked(email: String, password: String) {
        authenticationUseCase.register(email = email, password = password)
    }

    fun logoutClicked() {
        authenticationUseCase.logout()
    }

    fun addSkillStep1Finished(skillTitle: String, fireBaseUserId: String) {
        addSkillUseCase.step1Finished(skillTitle = skillTitle, userId = fireBaseUserId)
    }

    fun addSkillStep2Finished(skillDescription: String, fireBaseUserId: String) {
        addSkillUseCase.step2Finished(skillDescription = skillDescription, userId = fireBaseUserId)
    }

    fun addSkillStep3Finished(selfRating: Double, fireBaseUserId: String) {
        viewModelScope.launch {
            addSkillUseCase.step3Finished(selfRating = selfRating, userId = fireBaseUserId)
        }
    }

    fun createUserInFirebase(firebaseUserId: String, email: String) {
        authenticationUseCase.addUserToDatabase(firebaseUserId, email = email)
    }

}