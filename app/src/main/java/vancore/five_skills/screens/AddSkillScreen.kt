package vancore.five_skills.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.data.models.DropdownItem
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.data.models.SubcategoryItem
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
    if (currentCategory.isNotEmpty()) {
        viewModel.fetchSubcategoriesFor(currentCategory)
    }

    if (currentUserId != null) {

        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
            Scaffold(topBar = { FiveSkillsTitleText(titleText = "Add a Skill") }) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsWithImePadding()
                ) {
                    val (inputSection, buttonSection) = createRefs()

                    AddSkillPager(
                        addSkillStep = addSkillState.step,
                        titleText = currentTitleInput,
                        titleChange = titleChange,
                        descriptionText = currentDescriptionInput,
                        descriptionTextChange = descriptionChange,
                        ratingInputChange = ratingChange,
                        subcategoryChange = subcategoryChosen,
                        subcategoryList = viewModel.subcategoriesList,
                        categoryChange = categoryChosen,
                        categoryList = viewModel.categoriesList,
                        modifier = Modifier.constrainAs(inputSection) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.absoluteRight, margin = 24.dp)
                            start.linkTo(parent.absoluteLeft, margin = 24.dp)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.constrainAs(buttonSection) {
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                            start.linkTo(parent.absoluteLeft, margin = 24.dp)
                            end.linkTo(parent.absoluteRight, margin = 24.dp)
                            width = Dimension.fillToConstraints
                        }
                    ) {
                        val nextButtonText = remember { mutableStateOf("Next") }

                        // Back button
                        FiveSkillsButton(
                            buttonText = "Back",
                            onButtonClicked = {
                                if (addSkillState.step == AddSkillStep.Step1) {
                                    onFinishAddingSkill(addSkillState.itemToAdd)
                                } else {
                                    viewModel.addSkillStepBack()
                                }
                                nextButtonText.value = "Next"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 16.dp))

                        // Next Button
                        FiveSkillsButton(
                            buttonText = nextButtonText.value,
                            onButtonClicked = {
                                when (addSkillState.step) {
                                    AddSkillStep.Step1 -> {
                                        viewModel.addSkillStep1Finished(
                                            currentTitleInput,
                                            currentUserId
                                        )
                                        nextButtonText.value = "Next"
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
                            },
                            enabled = currentTitleInput.isNotEmpty(),
                            isLoginButtonHighlighted = currentTitleInput.isNotEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
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
    ratingInputChange: (String) -> Unit = {},
    subcategoryChange: (id: String) -> Unit = {},
    subcategoryList: List<DropdownItem> = listOf(),
    categoryChange: (id: String) -> Unit = {},
    categoryList: List<DropdownItem> = listOf(),
    modifier: Modifier
) {
    val padding = 32.dp

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {

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
            AddSkillStep.Step4 -> {
                Column {
                    FiveSkillsDropdownList(
                        label = "Category",
                        itemList = categoryList,
                        itemSelected = { firebaseId -> categoryChange(firebaseId) })
                    if (subcategoryList.isNotEmpty()) {
                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                        FiveSkillsDropdownList(
                            label = "Subcategory",
                            itemList = subcategoryList,
                            itemSelected = { firebaseId -> subcategoryChange(firebaseId) }
                        )
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
        Scaffold(topBar = { FiveSkillsTitleText(titleText = "Add a Skill") }) {
            ConstraintLayout(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxSize()
            ) {

                val (inputSection, buttonSection) = createRefs()

                AddSkillPager(
                    AddSkillStep.Step1,
                    titleText = "Title Text",
                    descriptionText = "Description Text",
                    modifier = Modifier.constrainAs(inputSection) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, margin = 24.dp)
                        start.linkTo(parent.start, margin = 24.dp)
                        width = Dimension.fillToConstraints
                    },
                    subcategoryList = listOf(
                        SubcategoryItem(1, "", "Subcategory Name"),
                        SubcategoryItem(1, "", "Subcategory Name"),
                        SubcategoryItem(1, "", "Subcategory Name")
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.constrainAs(buttonSection) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        start.linkTo(parent.absoluteLeft, margin = 24.dp)
                        end.linkTo(parent.absoluteRight, margin = 24.dp)
                        width = Dimension.fillToConstraints
                    }
                ) {
                    FiveSkillsButton(
                        onButtonClicked = {}, buttonText = "Back",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 16.dp))
                    FiveSkillsButton(
                        onButtonClicked = {},
                        buttonText = "Next",
                        isLoginButtonHighlighted = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}