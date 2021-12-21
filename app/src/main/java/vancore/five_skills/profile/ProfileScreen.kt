package vancore.five_skills.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.shared_components.FiveSkillsErrorText
import vancore.five_skills.shared_components.FiveSkillsTextInput
import vancore.five_skills.ui.theme.FiveSkillsTheme

@ExperimentalComposeUiApi
@Composable
fun ProfileScreen(fiveSkillsViewModel: FiveSkillsViewModel) {
    val authenticationState by fiveSkillsViewModel.authenticationState.collectAsState()

    authenticationState.currentUser
    val (currentUserInput, userChange) = remember { mutableStateOf("") }
    val (currentPasswordInput, passwordChange) = remember { mutableStateOf("") }

    Scaffold(topBar = { ProfileLoginTitle(titleText = "Login") }) {
        LoginInputs(
            userNameText = currentUserInput,
            passwordText = currentPasswordInput,
            onUserNameChanged = userChange,
            onPasswordChanged = passwordChange,
        )

        if (authenticationState.errorMessage.isNotEmpty()) {
            FiveSkillsErrorText(errorText = authenticationState.errorMessage)
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
    onUserNameChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {}
) {
    val placeHolderTextMail = "User Name / Email"
    val placeHolderTextPassword = "Password"
    val modifier = Modifier
        .padding(vertical = 16.dp, horizontal = 24.dp)
        .fillMaxWidth()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FiveSkillsTextInput(
            modifier = modifier,
            text = userNameText,
            onTextChange = onUserNameChanged,
            keyboardOption = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            placeHolder = placeHolderTextMail,
            label = placeHolderTextMail,
        )
        FiveSkillsTextInput(
            modifier = modifier,
            text = passwordText,
            onTextChange = onPasswordChanged,
            keyboardOption = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            placeHolder = placeHolderTextPassword,
            label = placeHolderTextPassword
        )
        FiveSkillsErrorText(errorText = "Not logged in.", paddingVertical = 16.dp)
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
            LoginInputs(userNameText = "Username", passwordText = "Password")
        }
    }
}