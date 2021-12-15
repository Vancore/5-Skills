package vancore.five_skills.category.ui

import vancore.five_skills.R
import vancore.five_skills.shared.CategoryItem
import vancore.five_skills.ui.theme.FiveSkillsTheme

import android.content.res.Configuration
import android.graphics.drawable.shapes.Shape
import android.util.Log
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.system.measureTimeMillis

@Composable
fun CategoryScreen(categoryViewModel: CategoryViewModel) {

    val list = categoryViewModel.categoriesList

    Scaffold(topBar = { TopBar() }) {
        CategoriesList(list = list)
    }
}

@Composable
fun TopBar() {
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
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(8.dp)) {
                Icon(
                    painter = painterResource(R.drawable.ic_person_outline_black),
                    contentDescription = "Profile",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 16.dp)) {
                Icon(
                    painter = painterResource(R.drawable.ic_cog_outline),
                    contentDescription = "Settings",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
        Divider(
            color = MaterialTheme.colors.onPrimary,
            thickness = 0.5.dp,
            modifier = Modifier.padding(top = 8.dp).alpha(0.5f)
        )
    }
}

@Composable
fun CategoriesList(list: List<CategoryItem>) {
    LazyColumn(
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
            .clickable { isClicked = !isClicked }
            .padding(vertical = 28.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = categoryItem.name,
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

/// Preview Section

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode List"
)
@Composable
fun CategoriesScreenPreview() {
    FiveSkillsTheme {
        Scaffold(topBar = { TopBar() }) {
            CategoriesList(
                List(3) { CategoryItem(it, "Name for $it") }
            )
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
        CategoryItem(CategoryItem(1, "Category 1"))
    }
}