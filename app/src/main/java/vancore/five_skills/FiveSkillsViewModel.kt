package vancore.five_skills

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import vancore.five_skills.authentication.AuthenticationState
import vancore.five_skills.authentication.AuthenticationUseCase
import vancore.five_skills.data.FiveSkillsRepository
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SubcategoryItem
import javax.inject.Inject

class FiveSkillsViewModel @Inject constructor(
    private val categoriesRepository: FiveSkillsRepository,
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    var categoriesList = mutableStateListOf<CategoryItem>()
        private set

    var subcategoriesList = mutableStateListOf<SubcategoryItem>()
        private set

    var isUserOnline = mutableStateOf(false)
        private set

    var authenticationState = authenticationUseCase.authenticationState

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

}