package vancore.five_skills.subcategory

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.shared_components.CategoryListEntry
import vancore.five_skills.data.models.SubcategoryItem
import vancore.five_skills.shared_components.TopBarSubCategory
import vancore.five_skills.shared_components.TopBarWithImage
import vancore.five_skills.ui.theme.FiveSkillsTheme

@Composable
fun SubcategoryScreen(
    categoryName: String,
    imageUrl: String,
    fiveSkillsViewModel: FiveSkillsViewModel,
    onSubcategorySelected: (String) -> Unit = {}
) {

    val list = fiveSkillsViewModel.subcategoriesList

    Scaffold(topBar = { TopBarSubCategory(categoryTitle = categoryName) }) {
        SubCategoriesList(list = list) { onSubcategorySelected(it) }
    }

}

@Composable
fun SubCategoriesList(list: List<SubcategoryItem>, onSubcategorySelected: (String) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) { subCategoryItem ->
            CategoryListEntry(
                descriptionText = subCategoryItem.name,
                id = subCategoryItem.firebaseId,
                iconURL = subCategoryItem.iconURL
            ) {
                onSubcategorySelected(it)
            }
        }
    }
}

@Preview(
    name = "Subcategory Screen - Dark Mode",
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Subcategory Screen - Light Mode",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun SubcategoryScreenPreview() {
    val list = listOf(
        SubcategoryItem(id = 1, firebaseId = "", name = "Subcategory 1"),
        SubcategoryItem(id = 1, firebaseId = "", name = "Subcategory 2"),
        SubcategoryItem(id = 1, firebaseId = "", name = "Subcategory 3"),
        SubcategoryItem(id = 1, firebaseId = "", name = "Subcategory 4")
    )
    FiveSkillsTheme {
        Scaffold(topBar = { TopBarSubCategory(categoryTitle = "Category Name") }) {
            SubCategoriesList(list = list) { }
        }
    }
}
