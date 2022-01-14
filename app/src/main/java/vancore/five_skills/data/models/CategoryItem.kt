package vancore.five_skills.data.models

data class CategoryItem(
    val id: Int,
    override val firebaseId: String,
    override val name: String,
    val iconURL: String,
    val topBarImage: String = "https://firebasestorage.googleapis.com/v0/b/five-skills-a3a1f.appspot.com/o/6313.jpeg?alt=media&token=3758f9cd-5e47-4cd0-b3f4-289a6f10bf8f"
): DropdownItem