package vancore.five_skills

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class FiveSkillsScreen(
    val icon: ImageVector
) {
    FiveSkillsSettings(
        icon = Icons.Filled.Settings,
    ),
    Profile(
        icon = Icons.Filled.PersonOutline,
    ),
    Skill(
        icon = Icons.Filled.Skateboarding,
    ),
    AddSkill(
        icon = Icons.Filled.Save,
    ),
    Categories(
        icon = Icons.Filled.AttachMoney,
    ),
    Subcategories(
        icon = Icons.Filled.MoneyOff,
    );

    companion object {
        fun fromRoute(route: String?): FiveSkillsScreen =
            when (route?.substringBefore("/")) {
                FiveSkillsSettings.name -> FiveSkillsSettings
                Profile.name -> Profile
                Skill.name -> Skill
                AddSkill.name -> AddSkill
                Categories.name -> Categories
                Subcategories.name -> Subcategories
                null -> Categories
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}