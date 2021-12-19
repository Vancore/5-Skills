package vancore.five_skills

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import vancore.five_skills.category.CategoryScreen
import vancore.five_skills.components.TopBar
import vancore.five_skills.subcategory.SubcategoryScreen
import vancore.five_skills.ui.theme.FiveSkillsTheme
import javax.inject.Inject

@AndroidEntryPoint
class FiveSkillsActivity : ComponentActivity() {

    @Inject
    lateinit var fiveSkillsViewModel: FiveSkillsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiveSkillsApp(fiveSkillsViewModel)
        }
    }
}

@Composable
fun FiveSkillsApp(fiveSkillsViewModel: FiveSkillsViewModel) {
    FiveSkillsTheme {
        val allScreens = FiveSkillsScreen.values().toList()
        val navController = rememberNavController()
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = FiveSkillsScreen.fromRoute(backStackEntry.value?.destination?.route)

        Scaffold(
            topBar = {
                if (currentScreen == FiveSkillsScreen.Categories) {
                    TopBar(
                        onOptionSelected = { screen -> navController.navigate(screen.name) }
                    )
                } else {
                    // ToDo : Either change here or put into different screens
                }

            } // here you can add a flexible tabbar
        ) { innerPadding ->
            FiveSkillsNavHost(
                navHostController = navController,
                modifier = Modifier.padding(innerPadding),
                viewModel = fiveSkillsViewModel
            )
        }

    }
}

@Composable
fun FiveSkillsNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: FiveSkillsViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = FiveSkillsScreen.Categories.name,
        modifier = modifier
    ) {
        composable(FiveSkillsScreen.FiveSkillsSettings.name) {

        }
        composable(FiveSkillsScreen.Profile.name) {

        }

        val skillsName = FiveSkillsScreen.Skill.name
        composable(
            route = "$skillsName/{id}",
            arguments = listOf(
                navArgument("data") {
                    type = NavType.StringType
                }
            )
        ) {

        }
        composable(FiveSkillsScreen.Categories.name) {
            CategoryScreen(fiveSkillsViewModel = viewModel) { categoryId ->
                navigateToSubCategory(navHostController, categoryId)
            }
        }
        composable(FiveSkillsScreen.Subcategories.name) {
            SubcategoryScreen(fiveSkillsViewModel = viewModel) { subcategoryId ->
                //navigateToSkillList
            }
        }
    }
}

private fun navigateToSubCategory(navController: NavHostController, subCategoryId: String) {
    navController.navigate("${FiveSkillsScreen.Subcategories.name}")
}

private fun navigateToSingleSkill(navController: NavHostController, skillId: String) {
    navController.navigate("${FiveSkillsScreen.Skill.name}/$skillId")
}