package vancore.five_skills.usecases

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import vancore.five_skills.data.FiveSkillsRepository
import vancore.five_skills.data.models.SkillItem
import javax.inject.Inject

data class ProfileSkillListState(
    val currentList: ArrayList<SkillItem> = arrayListOf(),
    val loadingState: ListState = ListState.Idle,
    val errorMessage: String = ""
)

class ProfileSkillListUseCase @Inject constructor(
    private val repository: FiveSkillsRepository
) {
    private val _profileSkillListState = MutableStateFlow(ProfileSkillListState())
    val profileSkillListState: StateFlow<ProfileSkillListState> =
        _profileSkillListState.asStateFlow()

    suspend fun fetchSkillListForUser(firebaseUserId: String) {
        _profileSkillListState.update {
            it.copy(loadingState = ListState.Loading)
        }
        val userProfileList = repository.fetchSkillListFromUser(firebaseUserId = firebaseUserId)
        _profileSkillListState.update {
            it.copy(currentList = userProfileList, loadingState = ListState.Idle)
        }
    }

    suspend fun deleteSkill(skillItem: SkillItem) {
        _profileSkillListState.update {
            it.copy(loadingState = ListState.Loading)
        }
        val deletionSuccessful = repository.deleteSkill(skillItem)
        if (_profileSkillListState.value.currentList.remove(skillItem) && deletionSuccessful) {
            _profileSkillListState.update {
                it.copy(currentList = it.currentList, loadingState = ListState.Idle)
            }
        } else {
            _profileSkillListState.update {
                it.copy(errorMessage = "Item could not be removed")
            }
        }
    }
}

enum class ListState {
    Loading,
    Idle
}