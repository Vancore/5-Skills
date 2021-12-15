package vancore.five_skills.category.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vancore.five_skills.category.data.CategoryListRepository
import vancore.five_skills.shared.CategoryItem
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class CategoryViewModel @Inject constructor(
    private val categoriesRepository: CategoryListRepository
) : ViewModel() {

    var categoriesList = mutableStateListOf<CategoryItem>()
        private set

    init {
        val timeInMillis = measureTimeMillis {
            viewModelScope.launch {
                categoriesList.addAll(categoriesRepository.loadCategories())
            }
        }
        println("Firebase Loading time: $timeInMillis")
    }
}