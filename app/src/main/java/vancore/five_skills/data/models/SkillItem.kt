package vancore.five_skills.data.models

data class SkillItem(
    val userId: String = "userId not provided",
    val skillId: String = "skillId not provided",
    val title: String = "Title not provided",
    val description: String = "Description not provided",
    val selfRating: Double = 0.0,
    val ranking: Int = 0,
    val backgroundImageUrl: String = ""
)
