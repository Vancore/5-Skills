package vancore.five_skills

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vancore.five_skills.data.FiveSkillsRepository
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SubcategoryItem
import javax.inject.Inject

class FiveSkillsViewModel @Inject constructor(
    private val categoriesRepository: FiveSkillsRepository
) : ViewModel() {

    var categoriesList = mutableStateListOf<CategoryItem>()
        private set

    var subcategoriesList = mutableStateListOf<SubcategoryItem>()
        private set

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