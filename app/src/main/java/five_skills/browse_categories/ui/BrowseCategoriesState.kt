package five_skills.five_skills.browse_categories.ui

import five_skills.five_skills.browse_categories.data.SkillCategory

data class BrowseCategoriesState(
  val categoriesToDispay: List<SkillCategory> = emptyList(),
  val loading: Boolean = false
)