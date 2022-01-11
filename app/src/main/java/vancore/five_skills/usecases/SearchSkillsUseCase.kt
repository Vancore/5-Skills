package vancore.five_skills.usecases

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import vancore.five_skills.data.FiveSkillsRepository
import vancore.five_skills.data.models.SkillItem
import javax.inject.Inject

data class SearchSkillsState(
    val currentList: ArrayList<SkillItem> = arrayListOf(),
    val loadingState: ListState = ListState.Idle,
    val errorMessage: String = ""
)

class SearchSkillsUseCase @Inject constructor(
    private val repository: FiveSkillsRepository
) {
    private val _searchSkillState = MutableStateFlow(SearchSkillsState())
    val searchSkillState: StateFlow<SearchSkillsState> =
        _searchSkillState.asStateFlow()

    suspend fun fetchSkillsForSubcategory(subcategoryId: String) {
        _searchSkillState.update {
            it.copy(loadingState = ListState.Loading)
        }
        val skillListSubcategory = repository.fetchSkillsForSubcategory(subcategoryId = subcategoryId)
        _searchSkillState.update {
            it.copy(currentList = skillListSubcategory, loadingState = ListState.Idle)
        }
    }
}

enum class SearchState {
    Loading,
    Idle
}