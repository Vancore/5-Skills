package vancore.five_skills.category.ui

import vancore.five_skills.R
import vancore.five_skills.shared.CategoryItem
import vancore.five_skills.ui.theme.FiveSkillsTheme

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CategoryScreen(categoryViewModel: CategoryViewModel) {

    val list = categoryViewModel.categoriesList

    Column {
        TopBar()
        CategoriesList(list = list)
    }
}

@Composable
fun TopBar() {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                color = MaterialTheme.colors.onPrimary
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_person_outline_black),
                    contentDescription = "Profile",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_cog_outline),
                    contentDescription = "Settings",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
        Divider(color = MaterialTheme.colors.onPrimary, thickness = 0.5.dp, modifier = Modifier.padding(top = 12.dp))
    }
}

@Composable
fun CategoriesList(list: List<CategoryItem>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) { categoryItem ->
            CategoryItem(categoryItem = categoryItem)
        }
    }
}

@Composable
fun CategoryItem(categoryItem: CategoryItem) {

    println("$categoryItem is called")
    // can be used for expanding
    // -> e.g. in Text, maxLines = if (isClicked ...)
    var isClicked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { isClicked = !isClicked },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = categoryItem.name,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.subtitle2,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(id = R.drawable.barbell),
            contentDescription = "Skill Icon",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle, same for border
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
        )
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
    Column {
        TopBar()
        CategoriesList(
            List(3) { CategoryItem(it, "Name for $it") }
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun SkillEntryPreview() {
    FiveSkillsTheme {
        CategoryItem(CategoryItem(1, "Category 1"))
    }
}