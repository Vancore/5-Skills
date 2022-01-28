package vancore.five_skills.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
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
    val skillList = searchSkillsState.currentList

    Scaffold(topBar = { TopBarSearchResult(subcategoryTitle = subcategoryName) }) {
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            items(items = searchSkillsState.currentList) { skillItem ->
                SearchResultItem(skillItem = skillItem, onItemClicked = { onSingleItemClicked(it) })
            }
        }
    }
}