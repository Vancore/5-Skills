package vancore.five_skills.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.ui.theme.FiveSkillsTheme
import vancore.five_skills.usecases.RegistrationState

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

    ConstraintLayout(modifier = Modifier.fillMaxHeight()) {

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
            FiveSkillsTextInput(
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
                bottom.linkTo(parent.bottom, margin = 24.dp)
                start.linkTo(parent.absoluteLeft, margin = 24.dp)
                end.linkTo(parent.absoluteRight, margin = 24.dp)
                width = Dimension.fillToConstraints
            }
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
    modifier: Modifier
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Button(onClick = onRegisterClicked) {
            Text(text = "Register")
        }
        Spacer(modifier = Modifier.padding(horizontal = 24.dp))
        Button(onClick = onLoginClicked) {
            Text(text = "Login")
        }
    }
}

@Composable
fun SkillList(
    onSkillClicked: (SkillItem) -> Unit = {},
    onAddSkillClicked: () -> Unit = {},
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

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            for (skill in skillItemList) {
                SkillListItem(skillItem = skill, isSelected = false) {
                    onSkillClicked(it)
                }
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
fun ProfileScreen() {
    FiveSkillsTheme {
        Scaffold(topBar = {
            ProfileTopBar(
                userEmailText = "Your.Email@gmail.com"
            )
        }) {
            val skillList = arrayListOf(
                SkillItem("userId", title = "Skill Title 1", selfRating = 1.0, ranking = 5),
                SkillItem("userId", title = "Skill Title 2", selfRating = 2.0, ranking = 4),
                SkillItem("userId", title = "Skill Title 3", selfRating = 3.0, ranking = 3),
                SkillItem("userId", title = "Skill Title 4", selfRating = 4.0, ranking = 2),
                //SkillItem("userId", "title 5", selfRating = 5.0, ranking = 1),
            )
            SkillList(skillItemList = skillList)
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