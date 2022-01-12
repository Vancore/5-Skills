package vancore.five_skills.data.models

data class SkillItem(
    // Firebase identification
    val userId: String = "userId not provided",
    val skillId: String = "skillId not provided",
    val subcategoryId: String = "subcategoryId not provided",
    val categoryId: String = "categoryId not provided",

    // Ui values
    val title: String = "Title not provided",
    val description: String = "Description not provided",
    val selfRating: Double = 0.0,
    val ranking: Int = 0,
    val backgroundImageUrl: String = "" // ToDo: Do I still need that?
)
