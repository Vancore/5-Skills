package vancore.five_skills.usecases

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import vancore.five_skills.data.FiveSkillsRepository
import vancore.five_skills.data.models.SkillItem
import javax.inject.Inject

data class AddSkillState(
    val itemToAdd: SkillItem = SkillItem(),
    val step: AddSkillStep = AddSkillStep.Step1,
    val loadingState: AddSkillLoadingState = AddSkillLoadingState.Idle,
    val errorMessage: String = ""
)

class AddSkillUseCase @Inject constructor(
    val repository: FiveSkillsRepository
) {
    private val _addSkillState = MutableStateFlow(AddSkillState())
    val addSkillState: StateFlow<AddSkillState> =
        _addSkillState.asStateFlow()

    fun step1Finished(skillTitle: String, userId: String) {
        _addSkillState.update {
            it.copy(
                itemToAdd = SkillItem(userId = userId, title = skillTitle),
                step = AddSkillStep.Step2
            )
        }
    }

    fun step2Finished(skillDescription: String, userId: String) {
        _addSkillState.update {
            it.copy(
                itemToAdd = SkillItem(
                    userId = userId,
                    title = it.itemToAdd.title,
                    description = skillDescription
                ),
                step = AddSkillStep.Step3
            )
        }
    }

    suspend fun step3Finished(selfRating: Double, userId: String) {
        _addSkillState.update {
            it.copy(
                itemToAdd = SkillItem(
                    userId = userId,
                    title = it.itemToAdd.title,
                    description = it.itemToAdd.description,
                    selfRating = selfRating
                ),
                step = AddSkillStep.Step1
            )
        }
        addSkillForUser(userId, _addSkillState.value.itemToAdd)
    }

    private suspend fun addSkillForUser(firebaseUserId: String, skillItem: SkillItem) {
        repository.addSkillForUser(firebaseUserId = firebaseUserId, skillItem = skillItem)
        // ToDo: Add error handling
        _addSkillState.update {
            it.copy(itemToAdd = skillItem, loadingState = AddSkillLoadingState.Idle)
        }
    }
}


enum class AddSkillLoadingState {
    Adding,
    Error,
    Idle
}

enum class AddSkillStep(val title: String = "", val description: String = "") {
    Step1(
        "Give your skill a short name",
        "This will be the first thing people will learn and see about your skill.\n\nBe precise, be focussed"
    ),
    Step2(
        "Give your skill a description",
        "Explain why you want to share your skill.\n\n" +
                "What makes your skill special?\n" +
                "What are things you can achieve with your skill?\n\n" +
                "Be detailed, be determined"
    ),
    Step3(
        "Give your skill a rating",
        "Give user an idea of your confidence.\n\nBe humble, be convincing."
    )
}