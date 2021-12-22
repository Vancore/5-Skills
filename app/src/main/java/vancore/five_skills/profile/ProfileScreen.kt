package vancore.five_skills.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import vancore.five_skills.shared_components.FiveSkillsErrorText
import vancore.five_skills.shared_components.FiveSkillsTextInput
import vancore.five_skills.ui.theme.FiveSkillsTheme

@ExperimentalComposeUiApi
@Composable
fun ProfileScreen(fiveSkillsViewModel: FiveSkillsViewModel) {
    val authenticationState by fiveSkillsViewModel.authenticationState.collectAsState()

    if (authenticationState.currentUser == null) {

        val (currentUserInput, userChange) = remember { mutableStateOf("") }
        val (currentPasswordInput, passwordChange) = remember { mutableStateOf("") }

        Scaffold(topBar = { ProfileLoginTitle(titleText = "Login") }) {
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
        Text(text = "Hello ${authenticationState.currentUser?.email}")
        Button(onClick = { fiveSkillsViewModel.logoutClicked() }) {
            Text(text = "Logout")
        }
    }
}

@Composable
fun ProfileLoginTitle(titleText: String) {
    val styledText = buildAnnotatedString {
        append(titleText)
        append(AnnotatedString(text = ".", spanStyle = SpanStyle(MaterialTheme.colors.secondary)))
    }
    Text(
        text = styledText,
        modifier = Modifier
            .padding(top = 40.dp, bottom = 24.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h1,
        fontSize = 40.sp
    )
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

@ExperimentalComposeUiApi
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun ProfileScreen() {
    FiveSkillsTheme {
        Scaffold(topBar = { ProfileLoginTitle(titleText = "Login") }) {
            LoginInputs(userNameText = "Username", passwordText = "Password", errorText = "")
        }
    }
}