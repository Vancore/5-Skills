package five_skills.categories.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import five_skills.five_skills.browse_categories.ui.SkillList
import five_skills.five_skills.ui.theme.FiveSkillsComposeTheme
import five_skills.shared.models.SkillItem
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesActivity : AppCompatActivity() {

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
  lateinit var categoriesViewModel: CategoriesViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      FiveSkillsComposeTheme {
        SkillList(skillList)
      }
    }
  }

  override fun onStart() {
    super.onStart()
    categoriesViewModel.loadCategories()
  }

}

