package five_skills.categories.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import five_skills.categories.data.CategoriesRepository
import five_skills.shared.models.CategoryItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
  private val categoriesRepository: CategoriesRepository,
//  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  var categoriesList = mutableStateListOf<CategoryItem>()
    private set

  fun loadCategories() {
    this.viewModelScope.launch {
      categoriesList.addAll(categoriesRepository.loadCategories())
    }
  }
}