package five_skills.categories.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import five_skills.categories.data.CategoriesRepository
import five_skills.shared.models.CategoryItem
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class CategoriesViewModel @Inject constructor(
  private val categoriesRepository: CategoriesRepository
) : ViewModel() {

  var categoriesList = mutableStateListOf<CategoryItem>()
    private set

  init {
    val timeInMillis = measureTimeMillis {
      this.viewModelScope.launch {
        categoriesList.addAll(categoriesRepository.loadCategories())
      }
    }
    println("Firebase Loading time: $timeInMillis")
  }
}