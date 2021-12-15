package vancore.five_skills.category.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import vancore.five_skills.ui.theme.FiveSkillsTheme
import javax.inject.Inject

@AndroidEntryPoint
class CategoryActivity : ComponentActivity() {

    @Inject
    lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiveSkillsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CategoryActivityScreen(categoryViewModel)
                }
            }
        }
    }
}

@Composable
fun CategoryActivityScreen(categoryViewModel: CategoryViewModel) {
    CategoryScreen(categoryViewModel = categoryViewModel)
}