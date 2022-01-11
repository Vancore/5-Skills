package vancore.five_skills.data.models

data class SubcategoryItem(
    val id: Int,
    val firebaseId: String,
    val name: String,
    val iconURL: String = "https://firebasestorage.googleapis.com/v0/b/five-skills-a3a1f.appspot.com/o/barbell.png?alt=media&token=dc7c7f3d-e3f3-4246-8a05-8160afd814aa",
    val backgroundURL: String = "https://firebasestorage.googleapis.com/v0/b/five-skills-a3a1f.appspot.com/o/6313.jpeg?alt=media&token=3758f9cd-5e47-4cd0-b3f4-289a6f10bf8f"
)