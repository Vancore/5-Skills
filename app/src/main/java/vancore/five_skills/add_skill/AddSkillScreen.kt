package vancore.five_skills.add_skill

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.shared_components.*
import vancore.five_skills.ui.theme.FiveSkillsTheme
import vancore.five_skills.usecases.AddSkillStep

@ExperimentalComposeUiApi
@Composable
fun AddSkillScreen(viewModel: FiveSkillsViewModel, onFinishAddingSkill: (SkillItem) -> Unit) {

    // Todo: If user logs out in the middle of adding a skill, he will be redirected / current state saved
    val authenticationState by viewModel.authenticationState.collectAsState()
    val profileSkillListState by viewModel.addSkillState.collectAsState()

    val currentUserId = authenticationState.currentUser?.uid
    val (currentTitleInput, titleChange) = remember { mutableStateOf("") }
    val (currentDescriptionInput, descriptionChange) = remember { mutableStateOf("") }
    val (currentRatingInput, ratingChange) = remember { mutableStateOf("") }

    if (currentUserId != null) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            AddSkillPager(
                addSkillStep = profileSkillListState.step,
                titleText = currentTitleInput,
                titleChange = titleChange,
                descriptionText = currentDescriptionInput,
                descriptionTextChange = descriptionChange,
                ratingInput = currentRatingInput,
                ratingInputChange = ratingChange,
                modifier = Modifier.weight(1f)
            )

            Button(onClick = {
                when (profileSkillListState.step) {
                    AddSkillStep.Step1 -> {
                        viewModel.addSkillStep1Finished(
                            currentTitleInput,
                            currentUserId
                        )
                    }
                    AddSkillStep.Step2 -> {
                        viewModel.addSkillStep2Finished(
                            currentDescriptionInput,
                            currentUserId
                        )
                    }
                    AddSkillStep.Step3 -> {
                        // Todo: Check for empty input
                        if (currentRatingInput.isNotEmpty()) {
                            viewModel.addSkillStep3Finished(
                                currentRatingInput.toDouble(),
                                currentUserId
                            )
                            onFinishAddingSkill(profileSkillListState.itemToAdd)
                        }
                    }
                }
            }) {
                Text(text = "Next")
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun AddSkillPager(
    addSkillStep: AddSkillStep,
    titleText: String,
    titleChange: (String) -> Unit = {},
    descriptionText: String,
    descriptionTextChange: (String) -> Unit = {},
    ratingInput: String,
    ratingInputChange: (String) -> Unit = {}, // ToDo Change to rating bar
    modifier: Modifier
) {
    val padding = 32.dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        FiveSkillsTitleText(titleText = "Add a Skill")
        FiveSkillsFullStopText(
            titleText = addSkillStep.title,
            modifier = Modifier.padding(bottom = padding)
        )
        when (addSkillStep) {
            AddSkillStep.Step1 -> FiveSkillsTextInput(
                text = titleText,
                onTextChange = titleChange,
                keyboardOption = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                placeHolder = "Name",
                modifier = Modifier.padding(bottom = padding)
            )
            AddSkillStep.Step2 -> FiveSkillsTextInput(
                text = descriptionText,
                onTextChange = descriptionTextChange,
                keyboardOption = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                placeHolder = "Description",
                modifier = Modifier.padding(bottom = padding)
            )
            AddSkillStep.Step3 -> FiveSkillsRatingBar()
        }
        FiveSkillsBodyCenter(titleText = addSkillStep.description)
    }
}


@ExperimentalComposeUiApi
@Preview(
    name = "Profile - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Profile - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun AddSkillScreenPreview() {
    FiveSkillsTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            AddSkillPager(
                AddSkillStep.Step1,
                titleText = "",
                descriptionText = "",
                ratingInput = "",
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {}) {

            }
        }
    }
}