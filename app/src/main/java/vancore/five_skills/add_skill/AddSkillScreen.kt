package vancore.five_skills.add_skill

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.data.models.SubcategoryItem
import vancore.five_skills.shared_components.*
import vancore.five_skills.ui.theme.FiveSkillsTheme
import vancore.five_skills.usecases.AddSkillStep

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AddSkillScreen(viewModel: FiveSkillsViewModel, onFinishAddingSkill: (SkillItem) -> Unit) {

    // Todo: If user logs out in the middle of adding a skill, he will be redirected / current state saved
    val authenticationState by viewModel.authenticationState.collectAsState()
    val addSkillState by viewModel.addSkillState.collectAsState()

    val currentUserId = authenticationState.currentUser?.uid
    val (currentTitleInput, titleChange) = remember { mutableStateOf("") }
    val (currentDescriptionInput, descriptionChange) = remember { mutableStateOf("") }
    val (currentRatingInput, ratingChange) = remember { mutableStateOf("3") }
    val (currentSubcategory, subcategoryChosen) = remember { mutableStateOf("") }
    val (currentCategory, categoryChosen) = remember { mutableStateOf("") }

    if (currentUserId != null) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 32.dp)
        ) {
            AddSkillPager(
                addSkillStep = addSkillState.step,
                titleText = currentTitleInput,
                titleChange = titleChange,
                descriptionText = currentDescriptionInput,
                descriptionTextChange = descriptionChange,
                ratingInput = currentRatingInput,
                ratingInputChange = ratingChange,
                subcategoryInput = currentSubcategory,
                subcategoryChange = subcategoryChosen,
                subcategoryList = viewModel.allSubcategories,
                categoryInput = currentCategory,
                categoryChange = categoryChosen,
                categoryList = viewModel.categoriesList,
                modifier = Modifier.weight(1f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 48.dp)
            ) {
                val nextButtonText = remember { mutableStateOf("Next") }

                // Back button
                Button(
                    onClick = {
                        if (addSkillState.step == AddSkillStep.Step1) {
                            onFinishAddingSkill(addSkillState.itemToAdd)
                        } else {
                            viewModel.addSkillStepBack()
                        }
                        nextButtonText.value = "Next"
                    }
                ) {
                    Text(text = "Back")
                }

                Spacer(modifier = Modifier.padding(horizontal = 16.dp))

                // Next Button
                Button(
                    onClick = {
                        when (addSkillState.step) {
                            AddSkillStep.Step1 -> {
                                viewModel.addSkillStep1Finished(
                                    currentTitleInput,
                                    currentUserId
                                )
                            }
                            AddSkillStep.Step2 -> {
                                viewModel.addSkillStep2Finished(
                                    currentDescriptionInput
                                )
                            }
                            AddSkillStep.Step3 -> {
                                viewModel.addSkillStep3Finished(
                                    currentRatingInput.toDouble()
                                )
                                nextButtonText.value = "Finish"
                            }
                            AddSkillStep.Step4 -> {
                                viewModel.addSkillStep4Finished(
                                    currentSubcategory, currentCategory
                                )
                                onFinishAddingSkill(addSkillState.itemToAdd)
                            }
                        }
                    }
                ) {
                    Text(text = nextButtonText.value)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AddSkillPager(
    addSkillStep: AddSkillStep,
    titleText: String,
    titleChange: (String) -> Unit = {},
    descriptionText: String,
    descriptionTextChange: (String) -> Unit = {},
    ratingInput: String,
    ratingInputChange: (String) -> Unit = {},
    subcategoryInput: String = "",
    subcategoryChange: (id: String) -> Unit = {},
    subcategoryList: List<SubcategoryItem> = listOf(),
    categoryInput: String = "",
    categoryChange: (id: String) -> Unit = {},
    categoryList: List<CategoryItem> = listOf(),
    modifier: Modifier
) {
    val padding = 32.dp
    var subcategoryExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }

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
            AddSkillStep.Step3 -> FiveSkillsRatingBar(
                rating = 3,
                modifier = Modifier.padding(bottom = padding),
                onRatingSelected = ratingInputChange
            )
            AddSkillStep.Step4 -> ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = {
                    subcategoryExpanded = !subcategoryExpanded
                }
            ) {
                TextField(
                    value = subcategoryInput,
                    modifier = Modifier.padding(bottom = padding),
                    readOnly = true,
                    onValueChange = { subcategoryChange(it) },
                    label = { Text(text = "Subcategory") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = subcategoryExpanded
                        ) {}
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = subcategoryExpanded,
                    onDismissRequest = {
                        subcategoryExpanded = false
                    }
                ) {
                    subcategoryList.forEach { subcategory ->
                        DropdownMenuItem(
                            onClick = {
                                subcategoryChange(subcategory.firebaseId)
                                subcategoryExpanded = false
                            }
                        ) {
                            Text(text = subcategory.name)
                        }
                    }
                }
            }
        }
        FiveSkillsBodyCenter(titleText = addSkillStep.description)
    }
}


@ExperimentalMaterialApi
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
                AddSkillStep.Step4,
                titleText = "Title Text",
                descriptionText = "Description Text",
                ratingInput = "",
                modifier = Modifier.weight(1f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 48.dp)
            ) {
                Button(onClick = {}) {
                    Text(text = "Back")
                }
                Spacer(modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = {}) {
                    Text(text = "Next")
                }
            }
        }
    }
}