package five_skills.browse_categories.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import five_skills.browse_categories.data.BrowseCategoriesRepository
import five_skills.five_skills.browse_categories.ui.BrowseCategoriesState
import javax.inject.Inject

class BrowseCategoriesViewModel @Inject constructor(
  private val browseCategoriesRepository: BrowseCategoriesRepository,
//  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  var uiState by mutableStateOf(value = BrowseCategoriesState(emptyList(), false))
    private set

  fun loadCategories() {
    browseCategoriesRepository.loadCategories()
  }
}