package five_skills.skill_browser.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.all_in_one.R
import dagger.hilt.android.AndroidEntryPoint
import five_skills.five_skills.ui.theme.FiveSkillsComposeTheme
import shared.models.SkillItem
import javax.inject.Inject

@AndroidEntryPoint
class BrowserActivity : AppCompatActivity(), BrowserItemClickListener {

  private val skillList = listOf(
    SkillItem("Skill Title 1", "Skill Description 1", 1),
    SkillItem("Skill Title 2", "Skill Description 1", 1),
    SkillItem("Skill Title 3", "Skill Description 1", 1),
    SkillItem("Skill Title 4", "Skill Description 1", 1),
    SkillItem("Skill Title 5", "Skill Description 1", 1),
    SkillItem("Skill Title 6", "Skill Description 1", 1),
    SkillItem("Skill Title 7", "Skill Description 1", 1),
    SkillItem("Skill Title 8", "Skill Description 1", 1),
    SkillItem("Skill Title 9", "Skill Description 1", 1),
    SkillItem("Skill Title 10", "Skill Description 1", 1),
    SkillItem("Skill Title 11", "Skill Description 1", 1),
    SkillItem("Skill Title 12", "Skill Description 1", 1),
    SkillItem("Skill Title 13", "Skill Description 1", 1),
    SkillItem("Skill Title 14", "Skill Description 1", 1),
    SkillItem("Skill Title 15", "Skill Description 1", 1),
    SkillItem("Skill Title 16", "Skill Description 1", 1),
    SkillItem("Skill Title 17", "Skill Description 1", 1),
  )

  @Inject
  lateinit var browserViewModel: BrowserViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      FiveSkillsComposeTheme {
        Text("Hello")
        SkillList(skillList)
      }
    }
  }

  override fun onStart() {
    super.onStart()
    browserViewModel.doSomething()
  }

  override fun onBrowserItemClicked(item: SkillItem) {
    // do some stuff
  }

}

@Composable
fun SkillList(list: List<SkillItem>) {
  LazyColumn(
    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    items(list) { skillEntry ->
      SkillEntry(skillItem = skillEntry)
    }
  }
}

@Composable
fun SkillEntry(skillItem: SkillItem) {

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
          text = skillItem.skillTitle,
          color = MaterialTheme.colors.secondary,
          style = MaterialTheme.typography.subtitle2
        )
        Text(
          text = skillItem.skillDescription,
          color = MaterialTheme.colors.secondaryVariant,
          style = MaterialTheme.typography.body2
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
fun SkillListPreview() {
  SkillList(
    listOf(
      SkillItem("Skill Title 1", "Skill Description 1", 1),
      SkillItem("Skill Title 2", "Skill Description 2", 1),
      SkillItem("Skill Title 3", "Skill Description 3", 1)
    )
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
    SkillEntry(SkillItem("Skill Title 1", "Skill Description 1", 1))
  }
}