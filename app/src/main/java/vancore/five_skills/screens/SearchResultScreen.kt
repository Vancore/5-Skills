package vancore.five_skills.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.data.models.SkillItem

@Composable
fun SearchResultScreen(
    viewModel: FiveSkillsViewModel,
    subcategoryName: String,
    subcategoryId: String,
    categoryId: String,
    subcategoryImageUrl: String,
    categoryImageUrl: String,
    onSingleItemClicked: (SkillItem) -> Unit
) {
    val searchSkillsState by viewModel.searchSkillsState.collectAsState()
    
    Scaffold(topBar = { TopBarSearchResult(subcategoryTitle = subcategoryName) }) {

    }
}