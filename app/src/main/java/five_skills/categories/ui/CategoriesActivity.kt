package five_skills.categories.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import five_skills.five_skills.ui.theme.FiveSkillsComposeTheme
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesActivity : AppCompatActivity() {

  @Inject
  lateinit var categoriesViewModel: CategoriesViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      FiveSkillsComposeTheme {
        CategoriesActivityScreen(categoriesViewModel)
      }
    }
  }

}

@Composable
fun CategoriesActivityScreen(categoriesViewModel: CategoriesViewModel) {
  CategoriesScreen(categoriesViewModel = categoriesViewModel)
}

