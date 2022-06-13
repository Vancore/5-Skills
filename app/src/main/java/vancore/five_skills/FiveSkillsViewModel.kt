package vancore.five_skills

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vancore.five_skills.usecases.AuthenticationUseCase
import vancore.five_skills.data.FiveSkillsRepository
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.data.models.SubcategoryItem
import vancore.five_skills.usecases.AddSkillUseCase
import vancore.five_skills.usecases.ProfileSkillListUseCase
import vancore.five_skills.usecases.SearchSkillsUseCase
import javax.inject.Inject

// @HiltViewModel use if you want to inject it separately
class FiveSkillsViewModel @Inject constructor(
    private val categoriesRepository: FiveSkillsRepository,
    private val authenticationUseCase: AuthenticationUseCase,
    private val profileSkillListUseCase: ProfileSkillListUseCase,
    private val searchSkillsUseCase: SearchSkillsUseCase,
    private val addSkillUseCase: AddSkillUseCase
) : ViewModel() {

    var categoriesList = mutableStateListOf<CategoryItem>()
        private set

    var subcategoriesList = mutableStateListOf<SubcategoryItem>()
        private set

    var allSubcategories = mutableStateListOf<SubcategoryItem>()
        private set

    var authenticationState = authenticationUseCase.authenticationState
    var profileSkillListState = profileSkillListUseCase.profileSkillListState
    var searchSkillsState = searchSkillsUseCase.searchSkillState
    var addSkillState = addSkillUseCase.addSkillState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesList.addAll(categoriesRepository.loadCategories())
            //fetchAllSubcategories() // currently not needed, might remove it
        }
    }

    fun fetchSubcategoriesFor(categoryID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            subcategoriesList.swapList(categoriesRepository.loadSubcategories(categoryId = categoryID))
        }
    }

    private suspend fun fetchAllSubcategories() {
        val fullListOfSubcategories = mutableListOf<SubcategoryItem>()
        for (category in categoriesList) {
            fullListOfSubcategories.addAll(categoriesRepository.loadSubcategories(categoryId = category.firebaseId))
        }
        allSubcategories.addAll(fullListOfSubcategories)
    }

    fun fetchSkillsForUser(firebaseUserId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            profileSkillListUseCase.fetchSkillListForUser(firebaseUserId)
        }
    }

    fun fetchSkillsForSubcategory(subcategoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchSkillsUseCase.fetchSkillsForSubcategory(subcategoryId = subcategoryId)
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

    fun addSkillStep2Finished(skillDescription: String) {
        addSkillUseCase.step2Finished(skillDescription = skillDescription)
    }

    fun addSkillStep3Finished(selfRating: Double) {
        addSkillUseCase.step3Finished(selfRating = selfRating)
    }

    fun addSkillStep4Finished(subcategoryId: String, categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addSkillUseCase.step4Finished(subcategoryId = subcategoryId, categoryId = categoryId)
        }
    }

    fun addSkillStepBack() {
        addSkillUseCase.stepBack()
    }

    fun createUserInFirebase(firebaseUserId: String, email: String) {
        authenticationUseCase.addUserToDatabase(firebaseUserId, email = email)
    }

    fun deleteSkill(skillItem: SkillItem) {
        viewModelScope.launch(Dispatchers.IO) {
            profileSkillListUseCase.deleteSkill(skillItem)
        }
    }

}