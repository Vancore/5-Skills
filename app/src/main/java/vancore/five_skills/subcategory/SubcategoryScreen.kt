package vancore.five_skills.subcategory

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.shared_components.CategoryListEntry
import vancore.five_skills.data.models.SubcategoryItem
import vancore.five_skills.shared_components.TopBarWithImage

@Composable
fun SubcategoryScreen(
    categoryName: String,
    imageUrl: String,
    fiveSkillsViewModel: FiveSkillsViewModel,
    onSubcategorySelected: (String) -> Unit = {}
) {

    val list = fiveSkillsViewModel.subcategoriesList

    Scaffold(topBar = { TopBarWithImage(titleText = categoryName, backgroundImageUrl = imageUrl) }) {
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
