package vancore.five_skills.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.shared_components.FiveSkillsErrorText
import vancore.five_skills.shared_components.FiveSkillsTextInput
import vancore.five_skills.ui.theme.FiveSkillsTheme

@Composable
fun ProfileScreen(fiveSkillsViewModel: FiveSkillsViewModel) {
    val authenticationState by fiveSkillsViewModel.authenticationState.collectAsState()

    authenticationState.currentUser
    var currentPasswordInput = ""
    var currentUserInput = ""

    Scaffold(topBar = { ProfileLoginTitle(titleText = "Login") }) {
        LoginInputs(
            onPasswordChanged = { currentPasswordInput = it },
            onUserNameChanged = { currentUserInput = it }
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

@Composable
fun LoginInputs(
    onUserNameChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {}
) {
    val placeHolderTextMail = "User Name / Email"
    val placeHolderTextPassword = "Password"
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FiveSkillsTextInput(
            placeHolder = placeHolderTextMail,
            label = placeHolderTextMail,
            value = "",
            onValueChange = onUserNameChanged,
        )
        FiveSkillsTextInput(
            placeHolder = placeHolderTextPassword,
            label = placeHolderTextPassword,
            value = "",
            onValueChange = onPasswordChanged
        )
        FiveSkillsErrorText(errorText = "Not logged in.", paddingVertical = 16.dp)
    }
}

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
            LoginInputs()
        }
    }
}