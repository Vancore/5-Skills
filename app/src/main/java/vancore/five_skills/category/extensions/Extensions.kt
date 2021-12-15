package vancore.five_skills.category.extensions

import com.google.firebase.firestore.QuerySnapshot
import vancore.five_skills.shared.CategoryItem

fun QuerySnapshot.getCategoryItems(): List<CategoryItem> {
    val listOfSkills = mutableListOf<CategoryItem>()
    for (category in this.documents) {
        listOfSkills.add(
            CategoryItem(
                id = category["id"].toString().toInt(),
                name = category["name"].toString()
            )
        )
    }
    return listOfSkills
}