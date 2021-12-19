package vancore.five_skills

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vancore.five_skills.data.CategoryListRepository
import vancore.five_skills.components.CategoryItem
import vancore.five_skills.components.SubCategoryItem
import javax.inject.Inject

class FiveSkillsViewModel @Inject constructor(
    private val categoriesRepository: CategoryListRepository
) : ViewModel() {

    var categoriesList = mutableStateListOf<CategoryItem>()
        private set

    var subCategoriesList = mutableStateListOf<SubCategoryItem>()
        private set

    init {
        viewModelScope.launch {
            categoriesList.addAll(categoriesRepository.loadCategories())
        }
    }

    fun fetchSubcategoriesFor(category: String) {
        viewModelScope.launch {
            // Todo: Load subcategories
            //subCategoriesList.addAll(categoriesRepository.loadS())
        }
    }
}