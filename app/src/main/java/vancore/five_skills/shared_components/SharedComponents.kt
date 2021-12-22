package vancore.five_skills.shared_components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import vancore.five_skills.FiveSkillsScreen
import vancore.five_skills.R
import vancore.five_skills.ui.theme.FiveSkillsTheme

@Composable
fun CategoryListEntry(descriptionText: String, id: String, itemClicked: (String) -> Unit = {}) {

    // can be used for expanding
    // -> e.g. in Text, maxLines = if (isClicked ...)
    var isClicked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isClicked = !isClicked
                itemClicked(id)
            }
            .padding(vertical = 28.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = descriptionText,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h6,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(id = R.drawable.barbell),
            contentDescription = "Skill Icon",
            modifier = Modifier
                .size(44.dp)
                // Clip image to be shaped as a circle, same for border
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.onPrimary, CircleShape)
        )
    }
}

@Composable
fun TopBar(
    //allScreens: List<FiveSkillsScreen>,
    onOptionSelected: (FiveSkillsScreen) -> Unit = {}
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp),
                color = MaterialTheme.colors.onPrimary
            )

            IconButton(
                onClick = { onOptionSelected(FiveSkillsScreen.Profile) },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_person_outline_black),
                    contentDescription = FiveSkillsScreen.Profile.name,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(
                onClick = { onOptionSelected(FiveSkillsScreen.FiveSkillsSettings) },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_cog_outline),
                    contentDescription = FiveSkillsScreen.FiveSkillsSettings.name,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
        FiveSkillsDivider()
    }
}

@Composable
fun FiveSkillsDivider() {
    Divider(
        color = MaterialTheme.colors.onPrimary,
        thickness = 0.5.dp,
        modifier = Modifier.alpha(0.7f)
    )
}

@ExperimentalComposeUiApi
@Composable
fun FiveSkillsTextInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit = {},
    onImeAction: () -> Unit= {},
    keyboardOption: KeyboardOptions,
    placeHolder: String,
    label: String,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text(text = placeHolder) },
        keyboardOptions = keyboardOption,
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}

@Composable
fun FiveSkillsErrorText(
    errorText: String,
    paddingVertical: Dp = 8.dp,
    paddingHorizontal: Dp = 24.dp
) {
    Text(
        text = errorText,
        color = MaterialTheme.colors.error,
        modifier = Modifier.padding(vertical = paddingVertical, horizontal = paddingHorizontal)
    )
}


//region: Preview

@Preview(
    name = "Error Text - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Error Text - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun FiveSkillsErrorText() {
    FiveSkillsTheme {
        FiveSkillsErrorText(errorText = "This is some error text.")
    }
}

@Preview(
    name = "Top Bar - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Top Bar - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun FiveSkillsTopBar() {
    FiveSkillsTheme {
        TopBar()
    }
}

//endregion