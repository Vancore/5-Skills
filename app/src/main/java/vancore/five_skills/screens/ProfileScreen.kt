package vancore.five_skills.screens

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.ui.theme.FiveSkillsTheme
import vancore.five_skills.ui.theme.Grey200
import vancore.five_skills.ui.theme.Primary
import vancore.five_skills.ui.theme.Primary_Dark
import vancore.five_skills.usecases.RegistrationState

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun ProfileScreen(
    fiveSkillsViewModel: FiveSkillsViewModel,
    onAddSkillClicked: () -> Unit,
    onSingleSkillClicked: (SkillItem) -> Unit,
) {
    val authenticationState by fiveSkillsViewModel.authenticationState.collectAsState()
    val profileSkillListState by fiveSkillsViewModel.profileSkillListState.collectAsState()

    // Safe current input to Database
    if (authenticationState.currentUser == null) {

        val (currentUserInput, userChange) = remember { mutableStateOf("") }
        val (currentPasswordInput, passwordChange) = remember { mutableStateOf("") }

        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
            Scaffold(topBar = { FiveSkillsTitleText(titleText = "Login") }) {
                LoginInputs(
                    userNameText = currentUserInput,
                    passwordText = currentPasswordInput,
                    errorText = authenticationState.errorMessage,
                    onUserNameChanged = userChange,
                    onPasswordChanged = passwordChange,
                    onLoginClicked = {
                        fiveSkillsViewModel.loginClicked(
                            currentUserInput,
                            currentPasswordInput
                        )
                    },
                    onRegisterClicked = {
                        fiveSkillsViewModel.registerClicked(
                            currentUserInput,
                            currentPasswordInput
                        )
                    }
                )
            }
        }
    } else {
        val firebaseUserId = authenticationState.currentUser!!.uid
        if (authenticationState.registrationState == RegistrationState.Idle) {
            fiveSkillsViewModel.fetchSkillsForUser(firebaseUserId)
        } else {
            fiveSkillsViewModel.createUserInFirebase(
                firebaseUserId,
                authenticationState.currentUser?.email!!
            )
        }

        Scaffold(topBar = {
            ProfileTopBar(
                userEmailText = authenticationState.currentUser?.email ?: "Your.Email@gmail.com"
            ) {
                fiveSkillsViewModel.logoutClicked()
            }
        }) {
            // ToDo: Empty List Screen?
            SkillList(
                skillItemList = profileSkillListState.currentList,
                onSkillClicked = onSingleSkillClicked,
                onSkillSwiped = { fiveSkillsViewModel.deleteSkill(it) },
                onAddSkillClicked = onAddSkillClicked
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginInputs(
    userNameText: String,
    passwordText: String,
    errorText: String,
    onUserNameChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLoginClicked: () -> Unit = {},
    onRegisterClicked: () -> Unit = {}
) {
    val placeHolderTextMail = "User Name / Email"
    val placeHolderTextPassword = "Password"
    val textModifier = Modifier
        .padding(vertical = 16.dp, horizontal = 24.dp)
        .fillMaxWidth()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
            .navigationBarsWithImePadding()
    ) {

        val (loginSection, buttonSection) = createRefs()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.constrainAs(loginSection) {
                top.linkTo(parent.top)
            }) {
            FiveSkillsTextInput(
                modifier = textModifier,
                text = userNameText,
                onTextChange = onUserNameChanged,
                keyboardOption = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                placeHolder = placeHolderTextMail,
                label = placeHolderTextMail,
            )
            FiveSkillsPasswordInput(
                modifier = textModifier,
                text = passwordText,
                onTextChange = onPasswordChanged,
                keyboardOption = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                placeHolder = placeHolderTextPassword,
                label = placeHolderTextPassword
            )
            if (errorText.isNotEmpty()) {
                FiveSkillsErrorText(errorText = errorText, paddingVertical = 16.dp)
            }
        }

        LoginButtons(
            onLoginClicked = onLoginClicked,
            onRegisterClicked = onRegisterClicked,
            modifier = Modifier.constrainAs(buttonSection) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.absoluteLeft, margin = 24.dp)
                end.linkTo(parent.absoluteRight, margin = 24.dp)
                width = Dimension.fillToConstraints
            },
            isLoginButtonHighlighted = userNameText.isNotEmpty() && passwordText.isNotEmpty()
        )
    }
}

@Composable
fun ProfileTopBar(
    userEmailText: String,
    onLogoutClicked: () -> Unit = {}
) {
    Column(modifier = Modifier.padding(top = 40.dp)) {
        val styledText = buildAnnotatedString {
            append("Logged in as")
            append(
                AnnotatedString(
                    text = ":",
                    spanStyle = SpanStyle(MaterialTheme.colors.secondary)
                )
            )
        }
        Text(
            text = styledText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            val listOfSymbolIndices = arrayListOf<Int>()
            userEmailText.toCharArray().forEachIndexed { index, char ->
                if (!char.toString().matches(Regex("\\w+"))) {
                    listOfSymbolIndices.add(index)
                }
            }
            val styledEmail = buildAnnotatedString {
                var stringIndex = 0
                for (index in listOfSymbolIndices) {
                    append("${userEmailText.subSequence(stringIndex, index)}")
                    append(
                        AnnotatedString(
                            text = "${userEmailText.subSequence(index, index + 1)}",
                            spanStyle = SpanStyle(MaterialTheme.colors.secondary)
                        )
                    )
                    stringIndex = index + 1
                }
                append("${userEmailText.subSequence(stringIndex, userEmailText.length)}")
            }
            Text(
                text = styledEmail,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp
            )
            IconButton(onClick = onLogoutClicked, modifier = Modifier.padding(end = 16.dp)) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "LogoutButton",
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
        FiveSkillsDivider()
    }
}

@Composable
fun LoginButtons(
    onLoginClicked: () -> Unit = {},
    onRegisterClicked: () -> Unit = {},
    isLoginButtonHighlighted: Boolean = false,
    modifier: Modifier
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        FiveSkillsButton(
            modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            buttonText = "Register",
            onButtonClicked = onRegisterClicked
        )
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        FiveSkillsButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            buttonText = "Login",
            onButtonClicked = onLoginClicked,
            isLoginButtonHighlighted
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun SkillList(
    onSkillClicked: (SkillItem) -> Unit = {},
    onAddSkillClicked: () -> Unit = {},
    onSkillSwiped: (SkillItem) -> Unit = {},
    skillItemList: ArrayList<SkillItem>
) {
    Column {
        val styledText = buildAnnotatedString {
            append("Your ${skillItemList.size} best skills")
            append(
                AnnotatedString(
                    text = ":",
                    spanStyle = SpanStyle(MaterialTheme.colors.secondary)
                )
            )
        }
        Text(
            text = styledText,
            modifier = Modifier.padding(all = 16.dp),
            fontSize = 14.sp
        )

//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
//        ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {

            items(skillItemList) { skill ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd) onSkillSwiped(skill)
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(StartToEnd),
                    dismissThresholds = { FractionalThreshold(0.5f) },
                    background = {
                        val icon = Icons.Default.Delete
                        val iconScale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)
                        val alignment = Alignment.CenterStart

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp, end = 16.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Icon",
                                modifier = Modifier.scale(scale = iconScale),
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                    },
                    dismissContent = {
                        SkillListItem(
                            skillItem = skill,
                            isSelected = dismissState.dismissDirection != null,
                            onSkillClicked = { onSkillClicked(it) },
                            backgroundColor = if (isSystemInDarkTheme()) Primary_Dark else Color.White
                        )
                    }
                )
            }
        }

        if (skillItemList.size < 5) {
            // show add button
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp)
            ) {
                AddSkillButton(onAddSkillClicked)
            }
        }
        FiveSkillsDivider()
    }
}


@ExperimentalMaterialApi
@Composable
fun SkillListItem(
    skillItem: SkillItem,
    isSelected: Boolean,
    onSkillClicked: (SkillItem) -> Unit,
    backgroundColor: Color = Primary
) {
    // Accompanist FlowRow maybe, to not need to set 52 DP fixed
    Row(
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth()
            .clickable { onSkillClicked(skillItem) }
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .background(
                    color = if (isSelected) {
                        MaterialTheme.colors.secondary
                    } else {
                        MaterialTheme.colors.onPrimary
                    }
                )
                .alpha(0.7f)
                .padding(start = 16.dp, end = 8.dp)
                .clip(RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Text(
            text = skillItem.title, maxLines = 1, textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body1,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Self-Rating",
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = skillItem.selfRating.toString(),
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 8.dp, end = 16.dp)
        ) {
            Text(
                text = "Ranking",
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = skillItem.ranking.toString(),
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Preview(
    name = "Profile with List - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Profile with List - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun ProfileScreen() {
    FiveSkillsTheme {
        Scaffold(topBar = {
            ProfileTopBar(userEmailText = "Your.Email@gmail.com")
        }) {
            val skillList = arrayListOf(
                SkillItem("userId", title = "Skill Title 1", selfRating = 1.0, ranking = 5),
                SkillItem("userId", title = "Skill Title 2", selfRating = 2.0, ranking = 4),
                SkillItem("userId", title = "Skill Title 3", selfRating = 3.0, ranking = 3),
                SkillItem("userId", title = "Skill Title 4", selfRating = 4.0, ranking = 2),
                //SkillItem("userId", "title 5", selfRating = 5.0, ranking = 1),
            )

            SkillList(
                skillItemList = skillList
            )
        }
    }
}

@ExperimentalComposeUiApi
@Preview(
    name = "Login - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Login - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun ProfileScreenLogin() {
    FiveSkillsTheme {
        Scaffold(topBar = { FiveSkillsTitleText(titleText = "Login") }) {
            LoginInputs(userNameText = "Username", passwordText = "Password", errorText = "")
        }
    }
}

@ExperimentalMaterialApi
@Preview(
    name = "Skill Item - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Skill Item - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun SkillListItemPreview() {
    FiveSkillsTheme {
        val skillItem = SkillItem(
            userId = "something",
            title = "Skill Item Title",
            selfRating = 4.0,
            ranking = 1
        )
        SkillListItem(
            skillItem = skillItem,
            isSelected = false,
            onSkillClicked = {}
        )
    }
}