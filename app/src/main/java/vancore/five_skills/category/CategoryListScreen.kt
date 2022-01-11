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
    categorySelected: (String, String, String) -> Unit,
    menuItemClicked: (String) -> Unit
) {

    val list = fiveSkillsViewModel.categoriesList

    Scaffold(topBar = {
        TopBar(onOptionSelected = { screen -> menuItemClicked(screen.name) })
    }) {
        CategoriesList(list = list, categorySelected = categorySelected)
    }

}


@Composable
fun CategoriesList(list: List<CategoryItem>, categorySelected: (String, String, String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) { categoryItem ->
            CategoryListEntry(
                descriptionText = categoryItem.name,
                iconURL = categoryItem.iconURL
            ) {
                categorySelected(categoryItem.firebaseId, categoryItem.name, categoryItem.topBarImage)
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
                List(3) {
                    CategoryItem(
                        id = it,
                        firebaseId = "FireBaseId",
                        name = "Name for $it",
                        iconURL = "https://firebasestorage.googleapis.com/v0/b/five-skills-a3a1f.appspot.com/o/6313.jpeg?alt=media&token=3758f9cd-5e47-4cd0-b3f4-289a6f10bf8f"
                    )
                }
            )
            { fireBaseId: String, categoryName: String, backgroundImage -> }
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
        CategoryListEntry(
            descriptionText = "Category 1",
            iconURL = "https://firebasestorage.googleapis.com/v0/b/five-skills-a3a1f.appspot.com/o/6313.jpeg?alt=media&token=3758f9cd-5e47-4cd0-b3f4-289a6f10bf8f"
        ) {}
    }
}