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

    fun step2Finished(skillDescription: String) {
        _addSkillState.update {
            it.copy(
                itemToAdd = SkillItem(
                    userId = it.itemToAdd.userId,
                    title = it.itemToAdd.title,
                    description = skillDescription
                ),
                step = AddSkillStep.Step3
            )
        }
    }

    fun step3Finished(selfRating: Double) {
        _addSkillState.update {
            it.copy(
                itemToAdd = SkillItem(
                    userId = it.itemToAdd.userId,
                    title = it.itemToAdd.title,
                    description = it.itemToAdd.description,
                    selfRating = selfRating
                ),
                step = AddSkillStep.Step4
            )
        }
    }

    suspend fun step4Finished(subcategoryId: String, categoryId: String) {
        _addSkillState.update {
            it.copy(
                itemToAdd = SkillItem(
                    userId = it.itemToAdd.userId,
                    title = it.itemToAdd.title,
                    description = it.itemToAdd.description,
                    selfRating = it.itemToAdd.selfRating,
                    subcategoryId = subcategoryId,
                    categoryId = categoryId
                ),
                step = AddSkillStep.Step1
            )
        }
        addSkillForUser(_addSkillState.value.itemToAdd)
    }

    private suspend fun addSkillForUser(skillItem: SkillItem) {
        repository.addSkillForUser(firebaseUserId = skillItem.userId, skillItem = skillItem)
        // ToDo: Add error handling
        _addSkillState.update {
            it.copy(itemToAdd = skillItem, loadingState = AddSkillLoadingState.Idle)
        }
    }

    fun stepBack() {
        _addSkillState.update {
            it.copy(
                step = when (it.step) {
                    AddSkillStep.Step1 -> AddSkillStep.Step1
                    AddSkillStep.Step2 -> AddSkillStep.Step1
                    AddSkillStep.Step3 -> AddSkillStep.Step2
                    AddSkillStep.Step4 -> AddSkillStep.Step3
                }
            )
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
    ),
    Step4(
        "Last but not least, choose which category your skill belongs to",
        "(I will add a feature to suggest categories/subcategories soon!)"
    )
}