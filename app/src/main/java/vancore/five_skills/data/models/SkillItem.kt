package vancore.five_skills.data.models

data class SkillItem(
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val selfRating: Double = 0.0,
    val ranking: Int = 0
)
