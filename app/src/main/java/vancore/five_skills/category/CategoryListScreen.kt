package vancore.five_skills.category

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.shared_components.CategoryListEntry
import vancore.five_skills.shared_components.TopBar
import vancore.five_skills.ui.theme.FiveSkillsTheme

@Composable
fun CategoryScreen(
    fiveSkillsViewModel: FiveSkillsViewModel,
    categorySelected: (String) -> Unit = {},
) {

    val list = fiveSkillsViewModel.categoriesList

    CategoriesList(list = list, categorySelected = categorySelected)

}


@Composable
fun CategoriesList(list: List<CategoryItem>, categorySelected: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) { categoryItem ->
            CategoryListEntry(descriptionText = categoryItem.name, categoryItem.firebaseId) {
                categorySelected(categoryItem.firebaseId)
            }
        }
    }
}

/// Preview Section

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode List"
)
@Composable
fun CategoriesScreenPreview() {
    FiveSkillsTheme {
        Scaffold(topBar = { TopBar(onOptionSelected = {}) }) {
            CategoriesList(
                List(3) { CategoryItem(it, "FireBaseId", "Name for $it") }
            )
            {}
        }
    }
}

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun SkillEntryPreview() {
    FiveSkillsTheme {
        CategoryListEntry("Category 1", "1") {}
    }
}