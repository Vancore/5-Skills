package vancore.five_skills

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import vancore.five_skills.NavArguments.CATEGORY_BACKGROUND_IMAGE_URL
import vancore.five_skills.NavArguments.CATEGORY_ID
import vancore.five_skills.NavArguments.CATEGORY_NAME
import vancore.five_skills.NavArguments.SKILL_BACKGROUND_IMAGE
import vancore.five_skills.NavArguments.SKILL_DESCRIPTION
import vancore.five_skills.NavArguments.SKILL_ID
import vancore.five_skills.NavArguments.SKILL_SELF_RATING
import vancore.five_skills.NavArguments.SKILL_TITLE
import vancore.five_skills.NavArguments.SKILL_USER_ID
import vancore.five_skills.NavArguments.SKILL_USER_IMAGE
import vancore.five_skills.NavArguments.SUBCATEGORY_BACKGROUND_IMAGE_URL
import vancore.five_skills.NavArguments.SUBCATEGORY_ID
import vancore.five_skills.NavArguments.SUBCATEGORY_NAME
import vancore.five_skills.screens.AddSkillScreen
import vancore.five_skills.screens.CategoryScreen
import vancore.five_skills.screens.ProfileScreen
import vancore.five_skills.screens.SearchResultScreen
import vancore.five_skills.screens.SkillScreen
import vancore.five_skills.screens.SubcategoryScreen
import vancore.five_skills.ui.theme.FiveSkillsTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@AndroidEntryPoint
class FiveSkillsActivity : ComponentActivity() {

    @Inject
    lateinit var fiveSkillsViewModel: FiveSkillsViewModel

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            FiveSkillsApp(fiveSkillsViewModel)
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun FiveSkillsApp(fiveSkillsViewModel: FiveSkillsViewModel) {
    FiveSkillsTheme {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
            }

            val allScreens = FiveSkillsScreen.values().toList()
            val navController = rememberNavController()
            val backStackEntry = navController.currentBackStackEntryAsState()
            val currentScreen = FiveSkillsScreen.fromRoute(backStackEntry.value?.destination?.route)

            FiveSkillsNavHost(
                navHostController = navController,
                //modifier = Modifier.padding(innerPadding),
                viewModel = fiveSkillsViewModel
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
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

        composable(FiveSkillsScreen.AddSkill.name) {
            AddSkillScreen(
                viewModel = viewModel,
                onFinishAddingSkill = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(FiveSkillsScreen.Profile.name) {
            // val exampleViewModel = hiltViewModel<FiveSkillsViewModel>() consider using this if I want to split my ViewModel
            ProfileScreen(
                fiveSkillsViewModel = viewModel,
                onAddSkillClicked = {
                    viewModel.subcategoriesList.clear()
                    navigateToAddSkill(navHostController)
                },
                onSingleSkillClicked = { skill ->
                    val placeHolderUrl =
                        if (skill.backgroundImageUrl.isEmpty()) PlaceHolderUrls.IMAGE_PLACEHOLDER else skill.backgroundImageUrl
                    navigateToSingleSkill(
                        navHostController,
                        skillUserId = skill.userId,
                        skillId = skill.userId,
                        skillTitle = skill.title,
                        skillDescription = skill.description,
                        skillSelfRating = skill.selfRating.toString(),
                        skillBackgroundImageUrl = placeHolderUrl,
                        skillUserImageUrl = placeHolderUrl
                    ) // ToDo: Check what to do when arguments are empty
                }
            )
        }

        composable(
            route = "${FiveSkillsScreen.Skill.name}/{$SKILL_USER_ID}/{$SKILL_ID}/{$SKILL_TITLE}/{$SKILL_DESCRIPTION}/{$SKILL_SELF_RATING}/{$SKILL_BACKGROUND_IMAGE}/{$SKILL_USER_IMAGE}",
            arguments = listOf(
                navArgument(SKILL_USER_ID) { type = NavType.StringType },
                navArgument(SKILL_ID) { type = NavType.StringType },
                navArgument(SKILL_TITLE) { type = NavType.StringType },
                navArgument(SKILL_DESCRIPTION) { type = NavType.StringType },
                navArgument(SKILL_SELF_RATING) { type = NavType.StringType },
                navArgument(SKILL_BACKGROUND_IMAGE) { type = NavType.StringType },
                navArgument(SKILL_USER_IMAGE) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments
            arguments?.getString(CATEGORY_NAME)
            SkillScreen(
                viewModel = viewModel,
                skillUserId = arguments?.getString(SKILL_USER_ID) ?: "userID not provided",
                skillId = arguments?.getString(SKILL_ID) ?: "skillId not provided",
                title = arguments?.getString(SKILL_TITLE) ?: "No Title provided",
                description = arguments?.getString(SKILL_DESCRIPTION) ?: "No Description provided",
                selfRating = arguments?.getString(SKILL_SELF_RATING) ?: "1.0",
                backgroundImageUrl = arguments?.getString(SKILL_BACKGROUND_IMAGE) ?: "",
                profileImageUrl = arguments?.getString(SKILL_USER_IMAGE) ?: ""
            ) // ToDo: Check what to do when arguments are empty
        }

        composable(FiveSkillsScreen.Categories.name) {
            CategoryScreen(
                fiveSkillsViewModel = viewModel,
                categorySelected = { categoryID, categoryName, categoryBackgroundImageUrl ->
                    viewModel.fetchSubcategoriesFor(categoryID)
                    navigateToSubCategory(
                        navHostController,
                        categoryName,
                        categoryID,
                        categoryBackgroundImageUrl
                    )
                },
                menuItemClicked = { screenName ->
                    navHostController.navigate(screenName)
                })
        }

        composable(
            route = "${FiveSkillsScreen.Subcategories.name}/{$CATEGORY_NAME}/{$CATEGORY_ID}/{$CATEGORY_BACKGROUND_IMAGE_URL}",
            arguments = listOf(
                navArgument(CATEGORY_NAME) { type = NavType.StringType },
                navArgument(CATEGORY_ID) { type = NavType.StringType },
                navArgument(CATEGORY_BACKGROUND_IMAGE_URL) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments
            arguments?.getString(CATEGORY_NAME)?.let { categoryName ->
                SubcategoryScreen(
                    categoryName = categoryName,
                    categoryId = arguments.getString(CATEGORY_ID) ?: "",
                    categoryImageUrl = arguments.getString(CATEGORY_BACKGROUND_IMAGE_URL) ?: "",
                    fiveSkillsViewModel = viewModel
                ) { subcategoryName, subcategoryId, subcategoryIconUrl, categoryId, categoryImageUrl ->
                    viewModel.fetchSkillsForSubcategory(subcategoryId)
                    navigateToSearchResult(
                        navController = navHostController,
                        subCategoryName = subcategoryName,
                        subCategoryId = subcategoryId,
                        categoryId = categoryId,
                        subCategoryBackgroundImageUrl = subcategoryIconUrl,
                        categoryImageUrl = categoryImageUrl
                    )
                }
            }
        }

        composable(
            route = "${FiveSkillsScreen.SearchResult.name}/{$SUBCATEGORY_NAME}/{$SUBCATEGORY_ID}/{$CATEGORY_ID}/{$SUBCATEGORY_BACKGROUND_IMAGE_URL}/{$CATEGORY_BACKGROUND_IMAGE_URL}",
            arguments = listOf(
                navArgument(SUBCATEGORY_NAME) { type = NavType.StringType },
                navArgument(SUBCATEGORY_ID) { type = NavType.StringType },
                navArgument(CATEGORY_ID) { type = NavType.StringType },
                navArgument(SUBCATEGORY_BACKGROUND_IMAGE_URL) { type = NavType.StringType },
                navArgument(CATEGORY_BACKGROUND_IMAGE_URL) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments
            SearchResultScreen(
                viewModel = viewModel,
                subcategoryName = arguments?.getString(SUBCATEGORY_NAME) ?: "",
                subcategoryId = arguments?.getString(SUBCATEGORY_ID) ?: "",
                categoryId = arguments?.getString(CATEGORY_ID) ?: "",
                subcategoryImageUrl = arguments?.getString(SUBCATEGORY_BACKGROUND_IMAGE_URL) ?: "",
                categoryImageUrl = arguments?.getString(CATEGORY_BACKGROUND_IMAGE_URL) ?: ""
            ) { skill ->
                val placeHolderUrl =
                    if (skill.backgroundImageUrl.isEmpty()) PlaceHolderUrls.IMAGE_PLACEHOLDER else skill.backgroundImageUrl
                navigateToSingleSkill(
                    navHostController,
                    skillUserId = skill.userId,
                    skillId = skill.userId,
                    skillTitle = skill.title,
                    skillDescription = skill.description,
                    skillSelfRating = skill.selfRating.toString(),
                    skillBackgroundImageUrl = placeHolderUrl,
                    skillUserImageUrl = placeHolderUrl
                )
            }
        }
    }
}

private fun navigateToSubCategory(
    navController: NavHostController,
    categoryName: String,
    categoryId: String,
    categoryBackgroundImageUrl: String
) {
    val encodedBackgroundImage = URLEncoder.encode(
        categoryBackgroundImageUrl,
        StandardCharsets.UTF_8.toString()
    )
    navController.navigate(
        "${FiveSkillsScreen.Subcategories.name}/$categoryName/$categoryId/$encodedBackgroundImage"
    )
}

private fun navigateToSearchResult(
    navController: NavHostController,
    subCategoryName: String,
    subCategoryId: String,
    categoryId: String,
    subCategoryBackgroundImageUrl: String,
    categoryImageUrl: String
) {
    val encodedSubcategoryImage = URLEncoder.encode(
        subCategoryBackgroundImageUrl,
        StandardCharsets.UTF_8.toString()
    )
    val encodedCategoryImage = URLEncoder.encode(
        categoryImageUrl,
        StandardCharsets.UTF_8.toString()
    )
    navController.navigate(
        "${FiveSkillsScreen.SearchResult.name}/$subCategoryName/$subCategoryId/$categoryId/$encodedSubcategoryImage/$encodedCategoryImage"
    )
}

private fun navigateToSingleSkill(
    navController: NavHostController,
    skillUserId: String,
    skillId: String,
    skillTitle: String,
    skillDescription: String,
    skillSelfRating: String,
    skillBackgroundImageUrl: String,
    skillUserImageUrl: String
) {
    val encodedBackGroundImage = URLEncoder.encode(
        skillBackgroundImageUrl,
        StandardCharsets.UTF_8.toString()
    )
    val encodedProfileImage = URLEncoder.encode(
        skillUserImageUrl,
        StandardCharsets.UTF_8.toString()
    )
    navController.navigate(
        "${FiveSkillsScreen.Skill.name}/$skillUserId/$skillId/$skillTitle/$skillDescription/$skillSelfRating/$encodedBackGroundImage/$encodedProfileImage"
    )
}

private fun navigateToAddSkill(navController: NavHostController) {
    navController.navigate(FiveSkillsScreen.AddSkill.name)
}

object NavArguments {
    const val CATEGORY_ID = "categoryID"
    const val CATEGORY_NAME = "categoryName"
    const val SUBCATEGORY_NAME = "subcategoryName"
    const val SUBCATEGORY_ID = "subcategoryID"
    const val ADD_SKILL_FOR_PROFILE = "addSkillForProfile"
    const val CATEGORY_BACKGROUND_IMAGE_URL = "backgroundImageUrl"
    const val SUBCATEGORY_BACKGROUND_IMAGE_URL = "subBackgroundImageUrl"

    // Detail
    const val SKILL_USER_ID = "skill_user_id"
    const val SKILL_ID = "skill_id"
    const val SKILL_TITLE = "skill_title"
    const val SKILL_DESCRIPTION = "skill_description"
    const val SKILL_SELF_RATING = "skill_self_rating"
    const val SKILL_BACKGROUND_IMAGE = "skill_background_image"
    const val SKILL_USER_IMAGE = "skill_user_image"

}

object PlaceHolderUrls {
    const val IMAGE_PLACEHOLDER =
        "https://firebasestorage.googleapis.com/v0/b/five-skills-a3a1f.appspot.com/o/6313.jpeg?alt=media&token=3758f9cd-5e47-4cd0-b3f4-289a6f10bf8f"
}