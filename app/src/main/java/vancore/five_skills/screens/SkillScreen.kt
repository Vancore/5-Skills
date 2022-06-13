package vancore.five_skills.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.ui.theme.FiveSkillsTheme

@ExperimentalComposeUiApi
@Composable
fun SkillScreen(
    viewModel: FiveSkillsViewModel,
    skillUserId: String,
    skillId: String,
    title: String,
    description: String,
    selfRating: String,
    backgroundImageUrl: String,
    profileImageUrl: String
) {
    val authenticationState by viewModel.authenticationState.collectAsState()
    var inEditMode by remember { mutableStateOf(false) }

    SkillScreenContent(
        isCurrentUser = authenticationState.currentUser?.uid == skillUserId,
        title = title,
        description = description,
        selfRating = selfRating.toDouble(),
        backgroundImageUrl = backgroundImageUrl,
        profileImageUrl = profileImageUrl,
        inEditMode = inEditMode
    )
}

@ExperimentalComposeUiApi
@Composable
fun SkillScreenContent(
    isCurrentUser: Boolean,
    title: String,
    description: String,
    selfRating: Double,
    backgroundImageUrl: String,
    profileImageUrl: String,
    inEditMode: Boolean
) {

    Scaffold(topBar = {
        TopBarWithImage(
            titleText = title,
            backgroundImageUrl = backgroundImageUrl,
            profileImageUrl = profileImageUrl
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            //if (isCurrentUser) inEditMode = true
            //if (inEditMode) inEditMode = false
        }) {
            if (isCurrentUser) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Button")
            } else if(isCurrentUser && inEditMode) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = "Message Button")
            } else {
            Icon(imageVector = Icons.Filled.Message, contentDescription = "Message Button")
            }
        }
    }
    ) {

        SkillContent(description = description, selfRating = selfRating)

    }
}

@ExperimentalComposeUiApi
@Composable
fun SkillContent(
    description: String,
    selfRating: Double
) {
    val titlePaddingModifier = Modifier.padding(top = 28.dp, start = 16.dp, end = 16.dp)
    val contentPaddingModifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)

    Column {
        FiveSkillsSubTitleText(
            titleText = "Description",
            modifier = titlePaddingModifier
        )

        FiveSkillsBodyText(
            titleText = description,
            modifier = contentPaddingModifier
        )

        FiveSkillsSubTitleText(
            titleText = "Self-Given Rating",
            modifier = titlePaddingModifier
        )

        FiveSkillsRatingBarNoSelection(
            rating = selfRating.toInt(),
            modifierView = contentPaddingModifier
        )

        FiveSkillsSubTitleText(
            titleText = "User Rating",
            modifier = titlePaddingModifier
        )

        FiveSkillsRatingBarNoSelection(
            rating = 4, // ToDo: Add real user rating
            modifierView = contentPaddingModifier
        )
    }
}

@ExperimentalComposeUiApi
@Preview(
    name = "Skill Detail - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Skill Detail - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun SkillScreenPreview() {
    FiveSkillsTheme {
        SkillScreenContent(
            isCurrentUser = true,
            title = "Skill Title",
            description = "Long Description, but not as long as it could be. I might need to add restrictions for users so they do not put in 2 Million lines of Text into this data field.",
            selfRating = 3.0,
            backgroundImageUrl = "https://firebasestorage.googleapis.com/v0/b/five-skills-a3a1f.appspot.com/o/barbell.png?alt=media&token=dc7c7f3d-e3f3-4246-8a05-8160afd814aa",
            profileImageUrl = "https://firebasestorage.googleapis.com/v0/b/five-skills-a3a1f.appspot.com/o/barbell.png?alt=media&token=dc7c7f3d-e3f3-4246-8a05-8160afd814aa",
            inEditMode = false
        )
    }
}