package five_skills.categories.ui

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
import com.example.all_in_one.R
import five_skills.five_skills.ui.theme.FiveSkillsComposeTheme
import five_skills.five_skills.ui.theme.Grey200
import five_skills.shared.models.CategoryItem

@Composable
fun CategoriesScreen(list: List<CategoryItem>) {

  Column {
    TopBar()

    CategoriesList(list = list)

  }
}

@Composable
fun TopBar() {
  Column {
    Row {
      Text(text = "Categories")
      IconButton(onClick = { /*TODO*/ }) {

      }
      IconButton(onClick = { /*TODO*/ }) {

      }
    }
    Divider(color = Grey200, thickness = 1.dp)
  }
}

@Composable
fun CategoriesList(list: List<CategoryItem>) {


  //viewModel.loadCategories() // then start LazyColumn or display empty screen

  LazyColumn(
    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    items(list) { skillEntry ->
      SkillEntry(categoryItem = skillEntry)
    }
  }
}

@Composable
fun SkillEntry(categoryItem: CategoryItem) {

  // can be used for expanding
  // -> e.g. in Text, maxLines = if (isClicked ...)
  var isClicked by remember { mutableStateOf(false) }

  Surface(
    shape = MaterialTheme.shapes.medium,
    elevation = 4.dp,
    modifier = Modifier
      .clickable { isClicked = !isClicked }
  ) {

    Row(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {


      Column {
        Text(
          text = categoryItem.name,
          color = MaterialTheme.colors.secondary,
          style = MaterialTheme.typography.subtitle2
        )
      }

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
}

@Preview
@Composable
fun CategoriesScreenPreview() {
  CategoriesScreen(
    List(3) { CategoryItem(it, "Name for $it") }
  )
}

@Preview(name = "Light Mode")
@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true,
  name = "Dark Mode"
)
@Composable
fun SkillEntryPreview() {
  FiveSkillsComposeTheme {
    SkillEntry(CategoryItem(1, "Category 1"))
  }
}