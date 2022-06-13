package vancore.five_skills.screens

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
import androidx.compose.ui.unit.dp
import vancore.five_skills.FiveSkillsViewModel

@ExperimentalComposeUiApi
@Composable
fun SkillEditScreen (
    viewModel: FiveSkillsViewModel,
    skillUserId: String,
    skillId: String,
    title: String,
    description: String,
    selfRating: String,
    backgroundImageUrl: String,
    profileImageUrl: String
) {}